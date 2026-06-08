<script setup lang="ts">
import Default from "@/layouts/Default.vue";
import {useRoute} from "vue-router";

const route = useRoute();
const layouts = {Default}
// Layout
// @ts-ignore
const Layout = layouts[route.meta.layout] || Default;
</script>

<template>
  <component :is="Layout">
    <RouterView/>
  </component>
</template>

<style scoped></style>
