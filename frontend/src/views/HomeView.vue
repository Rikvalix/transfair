<script setup>

import Button from "primevue/button";
import {ref} from 'vue';
import UploadForm from "@/components/business/UploadForm.vue";

const isFileDialogVisible = ref(false);
</script>

<template>
  <div class="min-h-screen flex items-center justify-center text-center">
    <div class="p-8">

      <h1 class="text-6xl font-bold mb-2 ">Transfair</h1>
      <p class="text-xl mb-16">
        Partagez vos fichiers de manière éphémère et sécurisée.
      </p>

      <Button
        label="Envoyer un fichier"
        icon="pi pi-cloud-upload"
        class="custom-pulse-btn px-8 py-4 text-xl font-semibold"
        @click="isFileDialogVisible = true"
        rounded
      />
    </div>
  </div>
  <UploadForm
    :model-value="isFileDialogVisible"
    @update:model-value="isFileDialogVisible = $event"
  />

</template>

<style scoped>

</style>


