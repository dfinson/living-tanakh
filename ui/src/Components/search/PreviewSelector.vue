<template>
    <section>
        <b-field grouped group-multiline>
            <button class="button field is-danger" @click="deleteAll"
                    :disabled="!selectedImages.length">
                <span>Clear All</span>
            </button>
            <button class="button field is-info"
                :disabled="!selectedImages.length" @click="downloadImages">
                <span>Download</span>
            </button>
        </b-field>

        <b-carousel :autoplay="false"  style="margin-top: 20px">

            <b-carousel-item v-for="(item, i) in selectedImages" :key="i">
                <a class="image">
                    <b-tooltip label="This will contain a description for the Image" style="margin-bottom: 40px"
                               position="is-bottom">
                        <img :src="getThumbnailUrl(i)" style="border-radius: 10px; image-orientation: from-image"/>
                    </b-tooltip>
                </a>

            </b-carousel-item>
        </b-carousel>
        <div >

          <v-progress-linear v-if="downloading"
              color="deep-purple accent-4"
              :indeterminate="true"
              rounded
              height="6"
              :active="true"
              fixed-header

          ></v-progress-linear>
          <h1 style="font-size:20px;font-family:Arial; color:#01579b;">{{selectedImages.length}} Image/s selected</h1>
          <h1 style="font-size:10px;font-family:Arial; color:black;">Select from the list the images you'd like to download</h1>

          <v-data-table style="margin-top: 10px"
              :headers="headers"
              :items="selectedImages"
                        :items-per-page="5"
                        dense
                        loading-text="Loading... Please wait"
              class="elevation-1"
          >

            <template v-slot:item.toBeDownloaded="{ item }">
              <v-simple-checkbox
                  v-model="item.toBeDownloaded"

              ></v-simple-checkbox>
            </template>
          </v-data-table>
          <v-snackbar
              v-model="errorMessage"
              :timeout="2000"
          >
            No Images Selected
          </v-snackbar>

        </div>
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
    import saveAs from 'file-saver';
    @Component
    export default class PreviewSelector extends Vue{

        public selectedImages: GalleriaImageItem[] = [];
        public showDetailIcon = false;
        public errorMessage = false;
        public downloading = false;
        public selected: boolean[] = [];




        @Prop()
        selectedImage: GalleriaImageItem;


        public headers = [
          { text: '', value: 'toBeDownloaded',align: 'start', sortable: false },
            {
          text: 'Image Description',
          value: 'title',
        },
          { text: 'Size', value: 'calories' },
       ]

      public deleteAll(): void{
        this.selectedImages = [];
        this.checkedRows = [];
        this.$emit('uncheck-all-images');
      }

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
                  if (this.selectedImages[i].title === temp.title)
                      repeat = true;
                  else
                    repeat = false;
                      }

              if(!repeat) {
                  console.log(temp.itemImageSrc)
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



       public deleteImage(title: string): void{
          console.log("previewSelector delete image " + title)
            this.selectedImages = this.selectedImages.filter(function(image) {
                return image.title !== title;
              });
         this.$buefy.toast.open({
           message: 'Image removed from selected images',
           type: 'is-danger',
           duration: 2000
         })
            }



        public checkedRows = [];

        public getThumbnailUrl(index: number): string{
            if(this.selectedImage !== undefined){
                return this.selectedImages[index].thumbnailImageSrc;}
            else{
                return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
            }

        }



      // http://localhost:5000/download-images?keys=['image1.jpg', 'image2.png', ...]
        public downloadImages(): void{
          this.downloading = true
            const keyArr: string[] = [];
            for(let i = 0; i < this.selectedImages.length; i++){
              if(this.selectedImages[i].toBeDownloaded)
                  keyArr.push(this.selectedImages[i].title);
            }
            console.log("downloading " + keyArr);
            if(keyArr.length > 0) {

              const data = JSON.stringify(keyArr);

              const xhr = new XMLHttpRequest();
              xhr.withCredentials = true;

              xhr.addEventListener("readystatechange", function () {
                if (this.readyState === this.DONE) {
                  console.log(this.responseText);
                }
              });

              xhr.open("POST", "http://livingtanakhapplicationde-env.eba-i3mkpska.eu-central-1.elasticbeanstalk.com/download-images");
              xhr.setRequestHeader("Content-Type", "application/json");
              xhr.responseType = "blob";
              xhr.send(data);
              xhr.onload = function () {

                const FileSaver = require('file-saver');
                const fileName = "Downloaded_Images.zip";
                FileSaver.saveAs(xhr.response, fileName);
                // alert(xhr.response);

              }
              this.downloading = false;
            }
            else {
              this.errorMessage = true;
            }

        }



    }
</script>

<style scoped>
    .tooltip {
        position: relative;
        display: inline-block;

    }

    .tooltip .tooltiptext {
        visibility: hidden;
        width: 120px;
        background-color:cornflowerblue;
        color: blue;
        text-align: center;
        border-radius: 6px;
        padding: 5px 0;
        font-family: "Trebuchet MS";
        font-size: 15px;

        /* Position the tooltip */
        position: absolute;
        z-index:2;
    }

    .tooltip:hover .tooltiptext {
        visibility: visible;
    }
    .icon{
        position: absolute;
        bottom:40px;
        left:45px;
    }
</style>