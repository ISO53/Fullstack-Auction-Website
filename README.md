# Fullstack Auction Website
 A complete simple auction website template that uses Java SpringBoot as a backend integrated with MongoDB and Redis. And it uses Vue as a frontend. 

## What you can do?
* You can become a member of this site (register) or log in if you are already a member. If you have logged in before, with Redis Session Management, you do not need to re-enter your e-mail and password every time you log in. With the hash saved in the cookie section of your browser Redis will remember you next time you visit the site.

* When you enter the site, you can bid as many times as you want before the auction expires. When others bid on these auctions, you can see the current price without refreshing your page (made using WebSocket). You can easily see the products in the auction, the photos of the products and the information about the products on the main page.

* The site is also responsive, meaning it displays properly even on a mobile device.

## How to run?
* Go to the folder you want to download the repository
* type ```git clone https://github.com/ISO53/Fullstack-Auction-Website```
* type ```docker compose up --build```

and that's it. If you don't want to reset the data every time you run the project, next time remove the ```--build``` part.

## Additional informations
### Technologies
This project is built using a robust and cutting-edge technology stack, which includes:
* Java Spring Boot: a powerful and popular framework for building scalable and high-performance applications, providing the foundation for our back-end services.
* Redis: a lightning-fast in-memory data structure store, enabling us to cache and process data in real-time.
* MongoDB: a flexible and scalable NoSQL database, providing a seamless and efficient way to store and manage our data.
* Vue.js: a progressive JavaScript framework for building user interfaces, allowing us to create dynamic and responsive front-end components.
* Docker: a powerful containerization technology, simplifying our deployment process and ensuring consistency across our development, testing, and production environments.

### Registration Key
key: ```6b8a1e77637bffbb984143aa6b630b869f6ad73daee00ba0c7670a4bcb32740e```
