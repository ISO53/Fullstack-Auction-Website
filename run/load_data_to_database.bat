mongosh "mongodb://localhost:27017/biddingDB" --quiet --eval="db.Product.load('../mongo_data/Product.json')"
mongosh "mongodb://localhost:27017/biddingDB" --quiet --eval="db.User.load('../mongo_data/User.json')"
mongosh "mongodb://localhost:27017/biddingDB" --quiet --eval="db.Auction.load('../mongo_data/Auction.json')"
pause
