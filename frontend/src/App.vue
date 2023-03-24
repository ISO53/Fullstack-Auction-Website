<template>
	<div id="app">
		<main id="main">
			<ErrorDiv id="error_div"></ErrorDiv>
			<MessageDiv id="message_div"></MessageDiv>

			<div class="top_bar">
				<img id="logo" @click="refresh()" src="../src/assets/logo.png" alt="" />
				<h1>Auction.co</h1>
				<img id="login_button" @click="openLoginRegister()" src="../src/assets/login.svg" alt="" />
			</div>

			<div class="auctions" id="auctions">
				<AuctionDiv id="auc_div_1"></AuctionDiv>
				<AuctionDiv id="auc_div_2"></AuctionDiv>
				<AuctionDiv id="auc_div_3"></AuctionDiv>
			</div>

			<div class="attribution" @click="openGithub()">Coded by ISO53</div>

			<div id="login_register_outer_div" class="login_register_outer_div" @click="handleClick">
				<LoginRegisterDiv id="login_register_div" class="login_register_div"></LoginRegisterDiv>
			</div>
		</main>
	</div>
</template>

<script>
import AuctionDiv from "@/components/AuctionDiv.vue";
import LoginRegisterDiv from "@/components/LoginRegisterDiv.vue";
import ErrorDiv from "@/components/ErrorDiv.vue";
import MessageDiv from "@/components/MessageDiv.vue";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

var times = [];

function getAuctionTimes() {
	fetch("http://localhost:8081/auction/getAll")
		.then((response) => response.json())
		.then((aucData) => {
			for (let i = 0; i < 3; i++) {
				times.push(aucData[i].startTime + aucData[i].period);
			}
		})
		.catch((error) => console.error(error));
}

function formatUnix(unixTime) {
	const seconds = Math.floor(unixTime);
	const days = Math.floor(seconds / (3600 * 24));
	const hours = Math.floor((seconds % (3600 * 24)) / 3600);
	const minutes = Math.floor((seconds % 3600) / 60);
	const remainingSeconds = seconds % 60;

	return `${days}:${hours.toString().padStart(2, "0")}:${minutes.toString().padStart(2, "0")}:${remainingSeconds.toString().padStart(2, "0")}`;
}

function startAuctionCountdown() {
	for (let i = 0; i < times.length; i++) {
		let auctionDiv = document.getElementById("auc_div_" + (i + 1));
		let remainingTime = times[i] - Math.floor(Date.now() / 1000);
		auctionDiv.children[0].children[1].innerHTML = "Time Left " + formatUnix(remainingTime);
	}
}

function listenWebSocketForBackendUpdates() {
	const socket = new SockJS("http://localhost:8081/websocket");
	const stompClient = new Client({
		brokerURL: "/websocket",
		webSocketFactory: () => socket,
	});

	stompClient.activate();
	stompClient.onConnect = function () {
		console.log("Connected to websocket.");

		stompClient.subscribe("/bid/update", function (message) {
			console.log("Received message: " + message.body);
			updateAuctionDiv(JSON.parse(message.body));
		});
	};
}

function updateAuctionDiv(data) {
	let aucDiv = document.getElementById(data.aucId).parentElement.parentElement;

	aucDiv.children[4].children[1].innerHTML = data.currentBid + "$";
	aucDiv.children[6].children[0].value = data.currentBid + parseInt(aucDiv.children[4].children[2].textContent.slice(0, -1));
	aucDiv.children[6].children[0].min = data.currentBid + parseInt(aucDiv.children[4].children[2].textContent.slice(0, -1));

	fetch("http://localhost:8081/user/get/" + data.bidderId)
		.then((response) => response.json())
		.then((userData) => {
			aucDiv.children[5].children[0].innerHTML = "Cuurent Bidder: " + userData.name + " " + userData.surname;
		})
		.catch((error) => console.error(error));
}

function loadAuctionsDatas() {
	fetch("http://localhost:8081/auction/getAll")
		.then((response) => response.json())
		.then((aucData) => {
			console.log(aucData);
			for (let i = 0; i < aucData.length; i++) {
				fetch("http://localhost:8081/product/get/" + aucData[i].productId)
					.then((response) => response.json())
					.then((proData) => {
						// Get the auction div
						let auctionDiv = document.getElementById("auc_div_" + (i + 1));

						// Load the product informations to the auc div
						auctionDiv.children[0].children[0].innerHTML = proData.name;
						auctionDiv.children[1].innerHTML = proData.description;
						auctionDiv.children[2].children[0].src = proData.imageURL;

						// Load the auction informations to the auc div
						auctionDiv.children[4].children[0].innerHTML = aucData[i].startingPrice + "$";
						auctionDiv.children[4].children[1].innerHTML = aucData[i].currentBid + "$";
						auctionDiv.children[4].children[2].innerHTML = aucData[i].minimumRaise + "$";

						// Pass the auction id to bid button for further use
						auctionDiv.children[6].children[1].id = aucData[i].id;

						// Make the minimum value for a user to bid to startingPrice + minimumRaise
						auctionDiv.children[6].children[0].min = aucData[i].currentBid + aucData[i].minimumRaise;
						auctionDiv.children[6].children[0].value = aucData[i].currentBid + aucData[i].minimumRaise;
					})
					.catch((error) => console.error(error));

				fetch("http://localhost:8081/user/get/" + aucData[i].currentBidderId)
					.then((response) => response.json())
					.then((userData) => {
						// Get the auction div
						let auctionDiv = document.getElementById("auc_div_" + (i + 1));

						// Make the minimum value for a user to bid to startingPrice + minimumRaise
						auctionDiv.children[5].children[0].innerHTML = "Current Bidder: " + userData.name + " " + userData.surname;
					})
					.catch((error) => console.error(error));
			}
		})
		.catch((error) => console.error(error));
}

function getSessionId() {
	const cookies = document.cookie.split(";");

	for (let i = 0; i < cookies.length; i++) {
		const cookie = cookies[i].trim();

		if (cookie.startsWith("SESSION_HASH")) {
			const sessionId = cookie.substring("SESSION_HASH=".length, cookie.length);
			return sessionId;
		}

		return null;
	}
}

function manageCurrentSession() {
	// Check if there is a previously saved session in cookies
	let sessionId = getSessionId();
	if (sessionId !== null) {
		fetch("http://localhost:8081/session/get/" + sessionId)
			.then((response) => response.json())
			.then((data) => {
				fetch("http://localhost:8081/user/login?email=" + data.email + "&password=" + data.password)
					.then((response) => response.json())
					.then((data) => sessionStorage.setItem("CURRENT_USER_ID", data.id))
					.catch((error) => console.error(error));
			})
			.catch((error) => {
				console.error("There was a problem with the fetch operation:", error);
			});
	}
}

export default {
	mounted() {
		loadAuctionsDatas();
		getAuctionTimes();

		manageCurrentSession();

		setInterval(startAuctionCountdown, 1000);
		startAuctionCountdown();

		listenWebSocketForBackendUpdates();
	},
	name: "App",
	components: {
		AuctionDiv,
		LoginRegisterDiv,
		ErrorDiv,
		MessageDiv,
	},
	methods: {
		openGithub() {
			window.open("https://github.com/ISO53");
		},
		refresh() {
			location.reload(true);
		},
		openLoginRegister() {
			let loginRegisterOuterDiv = document.getElementById("login_register_outer_div");
			let loginRegisterDiv = document.getElementById("login_register_div");

			loginRegisterOuterDiv.classList.add("show");
			loginRegisterDiv.classList.add("show");

			setTimeout(function () {
				loginRegisterOuterDiv.style.backdropFilter = "blur(10px) brightness(0.3)";
			}, 30);
		},
		handleClick(event) {
			let loginRegisterDiv = document.getElementById("login_register_div");
			let loginRegisterOuterDiv = document.getElementById("login_register_outer_div");

			if (!loginRegisterDiv.contains(event.target)) {
				loginRegisterDiv.classList.remove("show");
				loginRegisterOuterDiv.style.backdropFilter = "blur(0px)";
				setTimeout(function () {
					loginRegisterOuterDiv.classList.remove("show");
				}, 150);
			}
		},
	},
};
</script>

<style>
* {
	margin: 0 auto;
	padding: 0;
	box-sizing: border-box;
}

body {
	font-family: "monospace", sans-serif;
	overflow-x: hidden;
	background-image: url("../src/assets/background.svg");
	background-size: cover;
}

#main {
	width: 100%;
	display: block;
}

.auctions {
	margin-top: 100px;
	margin-bottom: 40px;
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: center;
	width: 80%;
	z-index: 1;
}

/* Styles for devices with a screen width of less than 700 pixels */
@media (max-width: 700px) {
	.auctions {
		flex-direction: column;
	}
}

.top_bar {
	position: fixed;
	top: 0;
	backdrop-filter: blur(10px) brightness(100%);
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: 50px;
	object-fit: contain;
	overflow: hidden;
	border-bottom: 1px solid rgba(255, 255, 255, 0.2);
	font-family: monospace;
	font-weight: 900;
	font-size: 1.1rem;
	color: white;
	z-index: 2;
}

#logo,
#login_button {
	object-fit: contain;
	height: 100%;
	cursor: pointer;
	filter: invert(32%) sepia(66%) saturate(4298%) hue-rotate(4deg) brightness(108%) contrast(107%);
}

.attribution {
	position: relative;
	bottom: 0;
	width: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	font-family: monospace;
	color: rgba(0, 0, 0, 0.5);
	height: 16px;
	cursor: pointer;
	color: chocolate;
}

.login_register_div {
	display: none;
}

.login_register_outer_div {
	backdrop-filter: blur(0px);
	width: 100vw;
	height: 100vh;
	position: absolute;
	top: 0;
	left: 0;
	display: none;
	justify-content: center;
	align-items: center;

	transition: backdrop-filter 150ms ease-in-out;
}
.show {
	display: flex;
}
</style>
