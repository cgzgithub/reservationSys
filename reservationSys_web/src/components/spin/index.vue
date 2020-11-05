<template>
  <div class="loading-wrapper">
    <div class="loading-inner">
      <div class="loading-spin">
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
        <div></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'loading'
};
</script>

<style lang="scss" scoped>

.loading-wrapper {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 10000;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(231, 228, 228, 0.37);
}

.loading-inner {
  padding: 30px 35px;
  border-radius: 8px;
  background-color: rgba(0, 0, 0, 0.6);
}

.loading-spin {
  color: #ccc;
  position: relative;
  width: 64px;
  height: 64px;
}
.loading-spin div {
  transform-origin: 32px 32px;
  animation: loading-spin 1.2s linear infinite;
}
.loading-spin div:after {
  content: " ";
  display: block;
  position: absolute;
  top: 3px;
  left: 29px;
  width: 5px;
  height: 14px;
  border-radius: 20%;
  background: rgb(158, 172, 195);
}
.loading-spin div:nth-child(1) {
  transform: rotate(0deg);
  animation-delay: -1.1s;
}
.loading-spin div:nth-child(2) {
  transform: rotate(30deg);
  animation-delay: -1s;
}
.loading-spin div:nth-child(3) {
  transform: rotate(60deg);
  animation-delay: -0.9s;
}
.loading-spin div:nth-child(4) {
  transform: rotate(90deg);
  animation-delay: -0.8s;
}
.loading-spin div:nth-child(5) {
  transform: rotate(120deg);
  animation-delay: -0.7s;
}
.loading-spin div:nth-child(6) {
  transform: rotate(150deg);
  animation-delay: -0.6s;
}
.loading-spin div:nth-child(7) {
  transform: rotate(180deg);
  animation-delay: -0.5s;
}
.loading-spin div:nth-child(8) {
  transform: rotate(210deg);
  animation-delay: -0.4s;
}
.loading-spin div:nth-child(9) {
  transform: rotate(240deg);
  animation-delay: -0.3s;
}
.loading-spin div:nth-child(10) {
  transform: rotate(270deg);
  animation-delay: -0.2s;
}
.loading-spin div:nth-child(11) {
  transform: rotate(300deg);
  animation-delay: -0.1s;
}
.loading-spin div:nth-child(12) {
  transform: rotate(330deg);
  animation-delay: 0s;
}
@keyframes loading-spin {
  0% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}

</style>

