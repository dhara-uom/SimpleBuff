@echo off
set "java_output="
setlocal enableDelayedExpansion
for /f "delims=" %%J in ('java -jar E:\FYP\projects\SimpleBuff\out\artifacts\SimpleBuff_jar\SimpleBuff.jar  %1 %2 %3 %4') do (
      set "java_output=!java_output! %%J" 
)
endlocal & set java_output=output=%java_output%
echo %java_output%