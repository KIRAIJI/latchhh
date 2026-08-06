param(
    [switch]$Upload,
    [string]$Port = 'COM3'
)

$ErrorActionPreference = 'Stop'

$arduinoCli = 'C:\Program Files\Arduino IDE\resources\app\lib\backend\resources\arduino-cli.exe'
$firmwareRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$sketchPath = Join-Path $firmwareRoot 'latch_tracker'
$buildPath = Join-Path $firmwareRoot 'build\latch_tracker'

& $arduinoCli compile `
    --fqbn 'esp32:esp32:esp32' `
    --warnings all `
    --build-path $buildPath `
    $sketchPath

if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}

if ($Upload) {
    & $arduinoCli upload `
        --fqbn 'esp32:esp32:esp32' `
        --port $Port `
        --input-dir $buildPath `
        $sketchPath
}

exit $LASTEXITCODE
