<template>
    <section>
        <b-field grouped group-multiline>
            <button class="button field is-danger" @click="selectedImages = [], checkedRows = []"
                    :disabled="!selectedImages.length">
                <span>Clear All</span>
            </button>
            <button class="button field is-info"
                :disabled="!selectedImages.length" @click="downloadSelectedImages">
                <span>Download</span>
            </button>
        </b-field>
        <b-carousel :autoplay="false"  style="margin-top: 20px">
            <b-carousel-item v-for="(item, i) in selectedImages" :key="i">
                <a class="image">
                   <img :src="getThumbnailUrl(i)" style="border-radius: 10px; image-orientation: from-image"/>
                </a>
            </b-carousel-item>
        </b-carousel>
        <span style="margin-top: 20px"><strong>Number Of Selected Images: {{selectedImages.length}}</strong></span>
    </section>
</template>
<script lang="ts">
    /*
     itemImageSrc: string;
    thumbnailImageSrc: string;
    alt: string;
    title: string;
    description: string;
     */
    import {Vue, Component, Prop, Watch} from "vue-property-decorator";
    import {GalleriaImageItem} from "@/api/dto";
    import axios from 'axios';


    @Component
    export default class PreviewSelector extends Vue{

        public selectedImages: GalleriaImageItem[] = [];
        public showDetailIcon = false;

        @Prop()
        selectedImage: GalleriaImageItem;
        @Prop()
        imageToBeDeleted: GalleriaImageItem;

        @Watch('selectedImage')
        onPropertyChanged(){
            let repeat = false;
          if(this.selectedImage.itemImageSrc !== ""){
              const temp  = new GalleriaImageItem( {
                  id:Math.floor(Math.random() * 100) ,
                  signedDownloadUrl: this.selectedImage.itemImageSrc,
                  key:this.selectedImage.alt,
                  description:this.selectedImage.description,
              });
              temp.toBeDownloaded = true;
              for(let i = 0; i < this.selectedImages.length; i++) {
                  if (this.selectedImages[i].itemImageSrc === temp.itemImageSrc)
                      repeat = true;
                      }
              if(!repeat) {
                  this.selectedImages.push(temp);
                  this.$buefy.toast.open({
                      message: 'Image Added to Selected Images',
                      type: 'is-success',
                      duration: 2000
                  })
              }
              //console.log(this.selectedImages.length);
          }

        }

        @Watch('imageToBeDeleted')
        onChanged(){
            if(this.imageToBeDeleted !== undefined && this.selectedImages.length > 0 && this.selectedImages !== undefined){
                this.selectedImages =  this.selectedImages.filter(item => item.itemImageSrc !== this.imageToBeDeleted.itemImageSrc)
                this.$buefy.toast.open({
                    message: 'Image removed from Selected Images',
                    type: 'is-danger',
                    duration: 2000
                })
            }
        }
        public checkedRows = [];

        public getThumbnailUrl(index: number): string{
            if(this.selectedImage !== undefined){
                return this.selectedImages[index].thumbnailImageSrc;}
            else{
                return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
            }

        }

        /*public downloadSelectedImages(): void{
            //for(let i = 0; i < this.selectedImages.length; i++){


        }*/






    }
</script>

<style scoped>
</style>