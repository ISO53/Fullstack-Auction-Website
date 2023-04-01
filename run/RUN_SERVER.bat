@echo off

title Run Server
echo Server is starting...

start run_vue.bat
start run_redis.bat
start run_mongo.bat
start run_spring.bat