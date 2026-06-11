import {ref} from "vue";
import type {FileUploadForm} from "@/type/file/FileUploadForm.ts";
import {type FileUploadSelectEvent, useToast} from "primevue";

export const useUploadFile = () => {

  const toast = useToast();
  const files= ref<File[]>([]);
  const form = ref<FileUploadForm>({
    title: "",
    description: "",
    maxDownloads: 1,
    expirationDate: new Date(),
    files: []
  })

  function removeFile(index: number) {
    files.value.splice(index, 1);
  }

  function getExtension(fileName: String) : String {
    const parts = fileName.split('.');
    const extension = parts[parts.length - 1] ;
    return extension == undefined ? '' : extension;
  }

  function handleFileSelect(e: FileUploadSelectEvent) {
    if (e.files.length != 0) {
      e.files.forEach((file: File) => {
        if (files.value.filter(f => f.name === file.name).length > 0) {
          toast.add({
            severity: 'error',
            summary: "Le fichier existe déja"
          })
          return
        }
        files.value.push(file);
      })
    }
  }

  function handleUpload() {

  }

  return {
    files,
    form,
    removeFile,
    getExtension,
    handleFileSelect,
    handleUpload
  }
}
