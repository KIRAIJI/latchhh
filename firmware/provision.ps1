param(
    [Parameter(Mandatory = $true)]
    [string]$TrackerUniqueId,
    [string]$Port = 'COM3'
)

$ErrorActionPreference = 'Stop'

if ($TrackerUniqueId -notmatch '^[A-Za-z0-9._-]{1,64}$') {
    throw 'TrackerUniqueId must contain 1-64 letters, numbers, dots, dashes, or underscores.'
}

$serialPort = [System.IO.Ports.SerialPort]::new(
    $Port,
    115200,
    [System.IO.Ports.Parity]::None,
    8,
    [System.IO.Ports.StopBits]::One
)
$serialPort.DtrEnable = $false
$serialPort.RtsEnable = $false
$serialPort.NewLine = "`n"
$serialPort.ReadTimeout = 100
$serialPort.WriteTimeout = 2000

try {
    $serialPort.Open()
    Start-Sleep -Milliseconds 200
    $serialPort.DiscardInBuffer()

    # A provisioned tracker may be in the existing USB charge-only light-sleep
    # loop. Reset it with GPIO0 released, then repeat the idempotent command
    # while the application starts so it cannot be lost during a sleep cycle.
    $serialPort.RtsEnable = $true
    Start-Sleep -Milliseconds 120
    $serialPort.RtsEnable = $false
    Start-Sleep -Milliseconds 200

    $response = [System.Text.StringBuilder]::new()
    $deadline = [DateTime]::UtcNow.AddSeconds(10)
    $nextWriteAt = [DateTime]::MinValue
    $confirmed = $false
    while ([DateTime]::UtcNow -lt $deadline) {
        $now = [DateTime]::UtcNow
        if ($now -ge $nextWriteAt) {
            $serialPort.WriteLine("SET_TRACKER_ID=$TrackerUniqueId")
            $nextWriteAt = $now.AddMilliseconds(200)
        }

        if ($serialPort.BytesToRead -gt 0) {
            [void]$response.Append($serialPort.ReadExisting())
            if ($response.ToString().Contains("Tracker ID saved: $TrackerUniqueId.")) {
                $confirmed = $true
                break
            }
        }
        Start-Sleep -Milliseconds 25
    }

    if (-not $confirmed) {
        throw "The tracker did not confirm provisioning. Device response: $response"
    }

    Write-Output "Provisioned $TrackerUniqueId on $Port."
} finally {
    if ($serialPort.IsOpen) {
        $serialPort.Close()
    }
    $serialPort.Dispose()
}
