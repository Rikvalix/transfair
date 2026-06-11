<script setup lang="ts">
import {computed} from "vue";
import {useUploadFile} from "@/composables/UploadFile.ts";
import {Form, FormField} from '@primevue/forms';
import {zodResolver} from '@primevue/forms/resolvers/zod';
import {z} from 'zod';

const props = defineProps({
  modelValue: {type: Boolean, required: true}
})

const emits = defineEmits(['update:modelValue'])

const {getExtension, removeFile, handleFileSelect, handleUpload, files, form} = useUploadFile();

const visible = computed({
  get: () => props.modelValue,
  set: (value) => emits('update:modelValue', value)
});

const resolver = zodResolver(
  z.object({
    title: z.string().min(10, {error: 'Le titre est requis'}),
    description: z.string().optional(),
    maxDownloads: z.number().min(1, 'Au moins 1 téléchargement'),
    expirationDate: z.date().refine((d) => d > new Date(), 'Date invalide'),
    files: z.array(z.any()).min(1, 'Au moins un fichier est requis')
  })
);


const onFormSubmit = ({valid}: { valid: boolean }) => {
  if (valid) {
    console.log('Données envoyées :');
  }
};

</script>

<template>
  <Dialog
    v-model:visible="visible"
    header="Envoyer des fichiers"
    class="w-full max-w-5xl m-2 "
    modal
    closable
  >
    <Form v-slot="$form" :initial-values="form" :resolver="resolver" @submit="onFormSubmit">
      <div class="flex flex-col md:flex-row gap-8 mb-4">

        <div class="flex-1 flex flex-col md:border-r  md:pr-8">
          <h3 class="font-semibold text-lg mb-4">Fichiers joints</h3>

          <div
            class="flex-auto border  rounded-md p-4  mb-4 min-h-50 flex flex-col gap-2 overflow-y-auto">
            <div
              v-for="(file,index) in files" :key="file.name"
              class="flex items-center justify-between  p-2 border  rounded shadow-sm">
              <span class="text-sm font-bold truncate max-w-[80%]">{{ file.name }}</span>
              <div>
                <chip>{{ getExtension(file.name).toUpperCase() }}</chip>
                <Button icon="pi pi-trash" severity="danger" text rounded aria-label="Supprimer"
                        @click="removeFile(index)"/>
              </div>
            </div>
          </div>

          <FileUpload
            choose-label="Ajouter un fichier"
            mode="basic"
            severity="primary"
            customUpload
            auto
            @select="handleFileSelect"
          />
        </div>

        <div class="flex-1 flex flex-col gap-4">
          <h3 class="font-semibold text-lg mb-2">Paramètres de l'envoi</h3>

          <FormField v-slot="$field" class="flex flex-col gap-2">
            <label for="title" class="font-semibold">Titre</label>
            <InputText id="title" placeholder="Titre de votre fichier" fluid/>
            <Message v-if="$field.invalid" severity="error" size="small" variant="simple">
              {{ $field.error?.message }}
            </Message>
          </FormField>

          <div class="flex flex-col gap-2">
            <label for="description" class="font-semibold">Description</label>
            <Textarea id="description" rows="3"/>
          </div>

          <div class="flex flex-col gap-2">
            <label for="maxDownloads" class="font-semibold">Nombre de téléchargements max</label>
            <InputNumber id="maxDownloads" :min="1" placeholder="10"/>
          </div>

          <div class="flex flex-col gap-2">
            <label for="expirationDate" class="font-semibold">Date d'expiration</label>
            <DatePicker id="expirationDate" showIcon dateFormat="dd/mm/yy"
                        placeholder="jj/mm/aaaa"/>
          </div>
        </div>

      </div>

      <div class="flex justify-end gap-2 mt-6 pt-4 border-t border-gray-200">
        <Button type="button" label="Annuler" severity="secondary"
                @click="() => emits('update:modelValue', false)"></Button>
        <Button icon="pi pi-upload" type="submit" label="Envoyer"/>
      </div>
    </Form>

  </Dialog>

</template>

<style scoped>

</style>
