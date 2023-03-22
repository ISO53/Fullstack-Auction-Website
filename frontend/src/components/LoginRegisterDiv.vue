<template>
	<div class="container" ref="container">
		<div class="form-container sign-up-container">
			<form action="#">
				<h1>Create Account</h1>
				<input id="reg_name" type="text" placeholder="Name" pattern="[A-Za-z]+" />
				<input id="reg_surname" type="text" placeholder="Surname" pattern="^[A-Za-z]+$" />
				<input id="reg_mail" type="email" placeholder="Email" pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$" />
				<input id="reg_password" type="password" placeholder="Password" />
				<button id="register" @click="register()">Sign Up</button>
			</form>
		</div>
		<div class="form-container sign-in-container">
			<form action="#">
				<h1>Sign in</h1>
				<input id="log_mail" type="email" placeholder="Email" />
				<input id="log_password" type="password" placeholder="Password" />
				<button id="login" @click="login()">Sign In</button>
			</form>
		</div>
		<div class="overlay-container">
			<div class="overlay">
				<div class="overlay-panel overlay-left">
					<h1>Welcome Back!</h1>
					<p>To keep connected with us please login with your personal info</p>
					<button class="ghost" ref="signIn" @click="leftPanelActive()">Sign In</button>
				</div>
				<div class="overlay-panel overlay-right">
					<h1>Hello, Friend!</h1>
					<p>Enter your personal details and start journey with us</p>
					<button class="ghost" ref="signUp" @click="rightPanelActive()">Sign Up</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
function showError(message) {
	let errDiv = document.getElementById("error_div");
	let errMsg = errDiv.children[0];
	errMsg.innerHTML = message;

	// Show the message
	errDiv.style.transform = "translateY(60px)";

	// Hide the message after 2 second
	setTimeout(function () {
		errDiv.style.transform = "translateY(-200px)";
	}, 4000);
}

function showMessage(message) {
	let msgDiv = document.getElementById("message_div");
	let msgText = msgDiv.children[0];
	msgText.innerHTML = message;

	// Show the message
	msgDiv.style.transform = "translateY(60px)";

	// Hide the message after 2 second
	setTimeout(function () {
		msgDiv.style.transform = "translateY(-200px)";
	}, 4000);
}

export default {
	methods: {
		rightPanelActive() {
			this.$refs.container.classList.add("right-panel-active");
		},
		leftPanelActive() {
			this.$refs.container.classList.remove("right-panel-active");
		},
		register() {
			let v_name = document.getElementById("reg_name").value;
			let v_surname = document.getElementById("reg_surname").value;
			let v_mail = document.getElementById("reg_mail").value;
			let v_password = document.getElementById("reg_password").value;

			const url = "http://localhost:8081/user/create";
			const data = {
				name: v_name,
				surname: v_surname,
				mail: v_mail,
				password: v_password,
			};

			fetch(url, {
				method: "POST",
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify(data),
			})
				.then((response) => response.json())
				.then((data) => {
					console.log(data);
					showMessage("Registration successful!");
				})
				.catch((error) => {
					console.error(error);
					showError("Registration failed!");
				});
		},
		login() {
			let v_mail = document.getElementById("log_mail").value;
			let v_password = document.getElementById("log_password").value;

			const url = "http://localhost:8081/user/login?email=" + v_mail + "&password=" + v_password;

			fetch(url, {
				method: "GET",
			})
				.then((response) => response.json())
				.then((data) => {
					console.log(data);
					showMessage("Login successful!");
				})
				.catch((error) => {
					console.error(error);
					showError("Login failed!");
				});
		},
	},
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
@import url("https://fonts.googleapis.com/css?family=Montserrat:400,800");

* {
	box-sizing: border-box;
}

body {
	background: #f6f5f7;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
	font-family: "Montserrat", sans-serif;
	height: 100vh;
	margin: -20px 0 50px;
}

h1 {
	font-weight: bold;
	margin: 0;
}

p {
	font-size: 14px;
	font-weight: 100;
	line-height: 20px;
	letter-spacing: 0.5px;
	margin: 20px 0 30px;
}

span {
	font-size: 12px;
}

a {
	color: #333;
	font-size: 14px;
	text-decoration: none;
	margin: 15px 0;
}

button {
	border-radius: 20px;
	border: 1px solid #ff4b2b;
	background-color: #ff4b2b;
	color: #ffffff;
	font-size: 12px;
	font-weight: bold;
	padding: 12px 45px;
	letter-spacing: 1px;
	text-transform: uppercase;
	transition: transform 80ms ease-in;
	cursor: pointer;
}

button:active {
	transform: scale(0.95);
}

button:focus {
	outline: none;
}

button.ghost {
	background-color: transparent;
	border-color: #ffffff;
}

form {
	background-color: #ffffff;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	padding: 0 50px;
	height: 100%;
	text-align: center;
}

input {
	background-color: #eee;
	border: none;
	padding: 12px 15px;
	margin: 8px 0;
	width: 100%;
}

.container {
	background-color: #fff;
	border-radius: 10px;
	box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
	position: relative;
	overflow: hidden;
	width: 768px;
	max-width: 100%;
	min-height: 480px;
}

.form-container {
	position: absolute;
	top: 0;
	height: 100%;
	transition: all 0.6s ease-in-out;
}

.sign-in-container {
	left: 0;
	width: 50%;
	z-index: 2;
}

.container.right-panel-active .sign-in-container {
	transform: translateX(100%);
}

.sign-up-container {
	left: 0;
	width: 50%;
	opacity: 0;
	z-index: 1;
}

.container.right-panel-active .sign-up-container {
	transform: translateX(100%);
	opacity: 1;
	z-index: 5;
	animation: show 0.6s;
}

@keyframes show {
	0%,
	49.99% {
		opacity: 0;
		z-index: 1;
	}

	50%,
	100% {
		opacity: 1;
		z-index: 5;
	}
}

.overlay-container {
	position: absolute;
	top: 0;
	left: 50%;
	width: 50%;
	height: 100%;
	overflow: hidden;
	transition: transform 0.6s ease-in-out;
	z-index: 100;
}

.container.right-panel-active .overlay-container {
	transform: translateX(-100%);
}

.overlay {
	background: #c42626;
	background: -webkit-linear-gradient(to right, #ff735a, #c42626);
	background: linear-gradient(to right, #ff735a, #c42626);
	background-repeat: no-repeat;
	background-size: cover;
	background-position: 0 0;
	color: #ffffff;
	position: relative;
	left: -100%;
	height: 100%;
	width: 200%;
	transform: translateX(0);
	transition: transform 0.6s ease-in-out;
}

.container.right-panel-active .overlay {
	transform: translateX(50%);
}

.overlay-panel {
	position: absolute;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
	padding: 0 40px;
	text-align: center;
	top: 0;
	height: 100%;
	width: 50%;
	transform: translateX(0);
	transition: transform 0.6s ease-in-out;
}

.overlay-left {
	transform: translateX(-20%);
}

.container.right-panel-active .overlay-left {
	transform: translateX(0);
}

.overlay-right {
	right: 0;
	transform: translateX(0);
}

.container.right-panel-active .overlay-right {
	transform: translateX(20%);
}
</style>
