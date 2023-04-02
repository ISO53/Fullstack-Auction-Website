#!/bin/bash

mongoimport --host mongo --db biddingDB -c Product --type json --file /Product.json --jsonArray
mongoimport --host mongo --db biddingDB -c User --type json --file /User.json --jsonArray
mongoimport --host mongo --db biddingDB -c Auction --type json --file /Auction.json --jsonArray