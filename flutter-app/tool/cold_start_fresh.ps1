$ErrorActionPreference = 'Stop'
$Package = 'com.latch.mobile'
$Activity = 'com.latch.mobile/.MainActivity'
$Device = 'emulator-5554'
$runDir = 'C:\Users\Rhea\Downloads\LATCH\tool\cold_start_captures\fresh_isolated_' + (Get-Date -Format 'HHmmss')
New-Item -ItemType Directory -Force -Path $runDir | Out-Null

adb -s $Device shell am force-stop $Package | Out-Null
Start-Sleep -Milliseconds 400
adb -s $Device shell pm clear $Package | Out-Null
Start-Sleep -Milliseconds 600

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

$prefAfter = adb -s $Device shell "run-as $Package cat shared_prefs/FlutterSharedPreferences.xml 2>&1"
Set-Content -Path (Join-Path $runDir 'prefs_after.txt') -Value $prefAfter
$focused = adb -s $Device shell dumpsys window windows | Select-String -Pattern 'mCurrentFocus|latch'
Set-Content -Path (Join-Path $runDir 'focus.txt') -Value ($focused -join "`n")
Write-Host "OUTPUT=$runDir"
