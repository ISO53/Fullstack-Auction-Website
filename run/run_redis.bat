echo Starting Redis at port 6379
cd "C:\Program Files\Redis"
redis-server redis.windows.conf
redis-cli monitor
pause