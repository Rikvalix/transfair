<script setup lang="ts">
import {computed} from "vue";

const props = defineProps({
  modelValue: {type: Boolean, required: true}
})

const emits = defineEmits(['update:modelValue'])

const visible = computed({
  get: () => props.modelValue,
  set: (value) => emits('update:modelValue', value)
});
</script>

<template>
  <Dialog
    v-model:visible="visible"
    header="Envoyer des fichiers"
    class="w-full max-w-5xl"
    modal
    closable
  >
    <div class="flex flex-col md:flex-row gap-8 mb-4">

      <div class="flex-1 flex flex-col md:border-r  md:pr-8">
        <h3 class="font-semibold text-lg mb-4">Fichiers joints</h3>

        <div
          class="flex-auto border  rounded-md p-4  mb-4 min-h-50 flex flex-col gap-2 overflow-y-auto">
          <div class="flex items-center justify-between  p-2 border  rounded shadow-sm">
            <span class="text-sm truncate">mon_fichier_important.pdf</span>
            <Button icon="pi pi-trash" severity="danger" text rounded aria-label="Supprimer"/>
          </div>
        </div>

        <FileUpload
          choose-label="Ajouter un fichier"
          mode="basic"
          icon="pi pi-file-plus"
          severity="primary"
          customUpload
          auto
        />
      </div>

      <div class="flex-1 flex flex-col gap-4">
        <h3 class="font-semibold text-lg mb-2">Paramètres de l'envoi</h3>

        <div class="flex flex-col gap-2">
          <label for="title" class="font-semibold">Titre</label>
          <InputText id="title" autocomplete="off" placeholder="Titre de votre fichier"/>
        </div>

        <div class="flex flex-col gap-2">
          <label for="description" class="font-semibold">Description</label>
          <Textarea id="description" rows="3" autocomplete="off"/>
        </div>

        <div class="flex flex-col gap-2">
          <label for="maxDownloads" class="font-semibold">Nombre de téléchargements max</label>
          <InputNumber id="maxDownloads" inputId="integeronly" :min="1" placeholder="10"/>
        </div>

        <div class="flex flex-col gap-2">
          <label for="expirationDate" class="font-semibold">Date d'expiration</label>
          <DatePicker id="expirationDate" showIcon dateFormat="dd/mm/yy" placeholder="jj/mm/aaaa"/>
        </div>
      </div>

    </div>

    <div class="flex justify-end gap-2 mt-6 pt-4 border-t border-gray-200">
      <Button type="button" label="Annuler" severity="secondary"
              @click="() => emits('update:modelValue', false)"></Button>
      <Button type="button" label="Envoyer"></Button>
    </div>
  </Dialog>

</template>

<style scoped>

</style>
