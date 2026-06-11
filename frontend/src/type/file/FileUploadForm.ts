export interface FileUploadForm {
  title: string;
  description: string;
  maxDownloads: number;
  expirationDate: Date;
  files: File[];
}
