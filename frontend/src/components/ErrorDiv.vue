<template>
	<div class="error_div">
		<h2 class="error"></h2>
	</div>
</template>

<script></script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.error_div {
	z-index: 1;
	display: flex;
	position: absolute;
	top: 0;
	left: 35%;
	align-self: center;
	margin: 0 auto;
	justify-content: center;
	align-items: center;
	border: 1px solid red;
	background-color: rgba(150, 0, 0, 0.7);
	border-radius: 10px;
	width: 30%;
	min-height: 50px;
	overflow: hidden;
	transform: translateY(-500px);
	transition: transform 250ms ease-out;
	color: white;
}

.error {
	font-size: 1rem;
	font-family: monospace;
}

/* Styles for devices with a screen width of less than 700 pixels */
@media (max-width: 700px) {
	.error_div {
		width: 100%;
		margin: 20px;
	}
}
</style>
