$ErrorActionPreference = 'Stop'
$Package = 'com.latch.mobile'
$Activity = 'com.latch.mobile/.MainActivity'
$Device = 'emulator-5554'
$runDir = 'C:\Users\Rhea\Downloads\LATCH\tool\cold_start_captures\returning_isolated_' + (Get-Date -Format 'HHmmss')
New-Item -ItemType Directory -Force -Path $runDir | Out-Null

adb -s $Device shell am force-stop $Package | Out-Null
Start-Sleep -Milliseconds 400

# Seed onboarding-complete prefs without clearing other state.
$prefs = "<?xml version='1.0' encoding='utf-8' standalone='yes' ?>`n<map>`n    <boolean name=`"flutter.latch_onboarding_completed`" value=`"true`" />`n</map>`n"
$tmp = Join-Path $env:TEMP 'latch_prefs.xml'
Set-Content -Path $tmp -Value $prefs -NoNewline
adb -s $Device push $tmp /data/local/tmp/latch_prefs.xml | Out-Null
adb -s $Device shell "run-as $Package sh -c 'mkdir -p shared_prefs; cp /data/local/tmp/latch_prefs.xml shared_prefs/FlutterSharedPreferences.xml'" | Out-Null
Remove-Item $tmp -Force

$prefCheck = adb -s $Device shell "run-as $Package cat shared_prefs/FlutterSharedPreferences.xml 2>&1"
Set-Content -Path (Join-Path $runDir 'prefs_before.txt') -Value $prefCheck

adb -s $Device shell am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n $Activity | Out-Null
$launchAt = Get-Date
$intervals = @(0, 50, 100, 200, 400, 800, 1200, 1800, 2500, 3500, 5000)
foreach ($ms in $intervals) {
    $wait = [math]::Max(0, $ms - ((Get-Date) - $launchAt).TotalMilliseconds)
    if ($wait -gt 0) { Start-Sleep -Milliseconds $wait }
    $file = Join-Path $runDir ("frame_{0}ms.png" -f $ms)
    cmd /c "adb -s $Device exec-out screencap -p > `"$file`""
}

Write-Host "OUTPUT=$runDir"
