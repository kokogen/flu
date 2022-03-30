docker run --restart=always -d --name redis \
   -v /Users/koko/opt/redis/etc/redis.conf:/usr/local/etc/redis/redis.conf \
   -v /Users/koko/opt/redis/data/log:/data/log \
   -v /Users/koko/opt/redis/data/bases:/data/bases \
   --network="default" \
   -p 6400:6379 redis redis-server /usr/local/etc/redis/redis.conf