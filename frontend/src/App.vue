<template>
	<div id="app">
		<main id="main">
			<div class="top_bar">
				<img id="logo" @click="refresh()" src="../src/assets/logo.png" alt="" />
				<h1>Auction.co</h1>
				<img id="login_button" @click="openLoginRegister()" src="../src/assets/login.svg" alt="" />
			</div>
			<div class="auctions">
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
export default {
	name: "App",
	components: {
		AuctionDiv,
		LoginRegisterDiv,
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
	font-family: "montserrat", sans-serif;
	overflow-x: hidden;
}

#main {
	height: 100vh;
	width: 100%;
	display: block;
}

.auctions {
	margin-top: 4vh;
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: center;
	height: 100vh;
	width: 80%;
}

.top_bar {
	position: fixed;
	top: 0;
	background-color: white;
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	width: 100%;
	height: 50px;
	object-fit: contain;
	overflow: hidden;
	box-shadow: 0px 0px 5px 0px black;
	font-family: monospace;
	font-weight: 900;
	font-size: 1.1rem;
}

#logo,
#login_button {
	object-fit: contain;
	height: 100%;
	cursor: pointer;
	filter: invert(32%) sepia(66%) saturate(4298%) hue-rotate(4deg) brightness(108%) contrast(107%);
}

.attribution {
	position: absolute;
	bottom: 0;
	width: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	font-family: monospace;
	color: rgba(0, 0, 0, 0.5);
	height: 16px;
	cursor: pointer;
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
