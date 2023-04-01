#!/bin/bash

mongoimport --host mongo --db biddingDB --collection Product --type json --file /Product.json --jsonArray
mongoimport --host mongo --db biddingDB --collection Auction --type json --file /Auction.json --jsonArray
mongoimport --host mongo --db biddingDB --collection User --type json --file /User.json --jsonArray

