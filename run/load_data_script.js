// Import data from User.json file
const userData = cat("/data/collections/User.json")
const users = JSON.parse(userData)
db.getCollection("User").insertMany(users)

// Import data from Product.json file
const productData = cat("/data/collections/Product.json")
const products = JSON.parse(productData)
db.getCollection("Product").insertMany(products)

// Import data from Auction.json file
const auctionData = cat("/data/collections/Auction.json")
const auctions = JSON.parse(auctionData)
db.getCollection("Auction").insertMany(auctions)