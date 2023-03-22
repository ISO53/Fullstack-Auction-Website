<template>
	<div class="auction">
		<h1 class="auc_name">Product</h1>
		<h2 class="desc">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Distinctio blanditiis ipsam rem odio.</h2>
		<div class="image_div">
			<img class="auc_image" src="https://ystyangin.com/wp-content/uploads/dummy-image-square.jpg" alt="" />
		</div>
		<div class="auc_headers">
			<h2 class="auc_head">Started At</h2>
			<h2 class="auc_head">Current</h2>
			<h2 class="auc_head">Minimum Raise</h2>
		</div>
		<div class="auc_infos">
			<h2 class="auc_info">##$</h2>
			<h2 id="current_bid" class="auc_info">##$</h2>
			<h2 class="auc_info">##$</h2>
		</div>
		<div class="button_div">
			<input type="number" name="bid" class="input" />
			<button class="button">BID</button>
		</div>
	</div>
</template>

<script></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.auction {
	width: 20vw;
	min-width: 250px;
	border-radius: 10px;
	backdrop-filter: blur(10px) brightness(300%);
	border: 1px solid rgba(255, 255, 255, 0.2);
	margin: 0px 15px 25px 15px;
	transition: border 150ms ease-out;
}
.auction:hover {
	border: 1px solid rgba(255, 255, 255, 0.4);
}
.auc_name {
	font-family: monospace;
	font-size: 1.6rem;
	text-align: center;
	margin: 15px;
	color: orangered;
}
.desc {
	font-family: monospace;
	font-size: 0.9rem;
	color: rgba(0, 0, 0, 0.5);
	text-align: justify;
	margin: 5px 25px 5px 25px;
	color: rgb(200, 200, 200);
}
.image_div {
	width: 100%;
	height: 100%;
}
.auc_image {
	width: 100%;
	height: 100%;
	object-fit: cover;
}
.auc_infos {
	display: flex;
	flex-direction: row;
	font-family: monospace;
	color: beige;
	justify-content: space-between;
}
.auc_info {
	width: 68px;
	text-align: center;
}
#current_bid {
	color: orange;
	font-size: 2rem;
}
.auc_headers {
	display: flex;
	flex-direction: row;
	font-family: monospace;
	color: grey;
	font-size: 0.5rem;
	justify-content: space-between;
}
.auc_head {
	width: 100px;
	text-align: center;
}
.button_div {
	margin: 5px 25px 5px 25px;
	height: 50px;
	margin: 10px;
	border-radius: 10px;
}
.button {
	border: 1px solid orangered;
	border-radius: 10px;
	width: 50%;
	height: 100%;
	font-family: monospace;
	font-size: 20px;
	color: white;
	background-color: orangered;
	transition: background-color 250ms ease-out;
	cursor: pointer;
}
.button:hover {
	background-color: rgb(255, 95, 37);
}
.input {
	width: 47%;
	height: 100%;
	border: 1px solid rgba(255, 255, 255, 0.4);
	border-radius: 10px;
	margin-right: 3%;
	text-align: center;
	font-family: monospace;
	font-size: 1.3rem;
	background-color: transparent;
	color: beige;
}
</style>
