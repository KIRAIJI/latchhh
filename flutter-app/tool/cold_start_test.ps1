param(
    [Parameter(Mandatory = $true)]
    [ValidateSet('fresh', 'returning')]
    [string]$Scenario
)

$ErrorActionPreference = 'Stop'
$Package = 'com.latch.mobile'
$Activity = 'com.latch.mobile/.MainActivity'
$Device = 'emulator-5554'
$OutDir = Join-Path $PSScriptRoot '..\tool\cold_start_captures' | Resolve-Path -ErrorAction SilentlyContinue
if (-not $OutDir) {
    $OutDir = Join-Path (Split-Path $PSScriptRoot -Parent) 'cold_start_captures'
}
New-Item -ItemType Directory -Force -Path $OutDir | Out-Null

$stamp = Get-Date -Format 'yyyyMMdd_HHmmss'
$runDir = Join-Path $OutDir "$Scenario`_$stamp"
New-Item -ItemType Directory -Force -Path $runDir | Out-Null

function Invoke-Adb([string[]]$AdbArgs) {
    & adb -s $Device @AdbArgs
    if ($LASTEXITCODE -ne 0) {
        throw "adb failed: adb -s $Device $($AdbArgs -join ' ')"
    }
}

Write-Host "Scenario: $Scenario"
Write-Host "Output: $runDir"

Invoke-Adb @('shell', 'am', 'force-stop', $Package)
Start-Sleep -Milliseconds 500

if ($Scenario -eq 'fresh') {
    Write-Host 'Clearing app data (fresh install simulation)...'
    Invoke-Adb @('shell', 'pm', 'clear', $Package)
    Start-Sleep -Milliseconds 800
} else {
    Write-Host 'Marking onboarding complete (returning user)...'
    $prefs = @"
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <boolean name="flutter.latch_onboarding_completed" value="true" />
</map>
"@
    $tmp = Join-Path $env:TEMP 'latch_prefs.xml'
    Set-Content -Path $tmp -Value $prefs -Encoding UTF8 -NoNewline
    Invoke-Adb @('push', $tmp, '/data/local/tmp/latch_prefs.xml') | Out-Null
    Invoke-Adb @(
        'shell',
        'run-as', $Package,
        'sh', '-c',
        'mkdir -p shared_prefs && cp /data/local/tmp/latch_prefs.xml shared_prefs/FlutterSharedPreferences.xml'
    )
    Remove-Item $tmp -Force
}

Write-Host 'Cold launching from launcher intent...'
Invoke-Adb @(
    'shell', 'am', 'start',
    '-a', 'android.intent.action.MAIN',
    '-c', 'android.intent.category.LAUNCHER',
    '-n', $Activity
) | Out-Null

$intervals = @(0, 80, 160, 320, 640, 1200, 2000, 3500)
$launchAt = Get-Date
foreach ($ms in $intervals) {
    $elapsed = ((Get-Date) - $launchAt).TotalMilliseconds
    $wait = [math]::Max(0, $ms - $elapsed)
    if ($wait -gt 0) { Start-Sleep -Milliseconds $wait }
    $file = Join-Path $runDir ("t{0:D4}ms.png" -f $ms)
    cmd /c "adb -s $Device exec-out screencap -p > `"$file`""
    if (-not (Test-Path $file) -or (Get-Item $file).Length -lt 1000) {
        throw "Screenshot capture failed at ${ms}ms"
    }
    Write-Host "Captured $file ($((Get-Item $file).Length) bytes)"
}

Write-Host "DONE:$runDir"
