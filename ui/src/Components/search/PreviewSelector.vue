<template>
    <section>
        <b-field grouped group-multiline>
            <button class="button field is-danger" @click="selectedImages = [], checkedRows = []"
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
            <span style="margin-top: 20px"><strong>Number Of Selected Images: {{selectedImages.length}}</strong></span>
          <v-data-table style="margin-top: 10px"
              :headers="headers"
              :items="selectedImages"
                        :items-per-page="5"
                        dense
              class="elevation-1"
          ></v-data-table>
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


    @Component
    export default class PreviewSelector extends Vue{

        public selectedImages: GalleriaImageItem[] = [];
        public showDetailIcon = false;

        @Prop()
        selectedImage: GalleriaImageItem;
        @Prop()
        imageToBeDeleted: GalleriaImageItem;

        public headers = [  {
          text: 'Image Description',
          align: 'start',
          sortable: false,
          value: 'title',
        },
          { text: 'Size', value: 'calories' }]
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

        public downloadImages(): void{
            const keyArr: string[] = [];
            for(let i = 0; i < this.selectedImages.length; i++){
                keyArr.push(this.selectedImages[i].title);
            }

            const opts = {
                method: "POST",
                credentials: "include",
                headers: { "Content-Type": "application/json" },
                body:'SamA-c17-Soch05.jpeg'
            };
            fetch('http://localhost:5000/download-images', {
                method: "POST",
                credentials: "include",
                headers: { "Content-Type": "application/json" },
                body: 'SamA-c17-Soch05.jpeg'
            }).then(res => console.log("yo!!!"));
            /*
            const axios = require('axios');
                // Default options are marked with *
            axios.post('http://localhost:5000/download-images', {
               keys:['SamA-c17-Soch05.jpeg']
            }).then((res: any)=>{
                console.log("yo")
            })*/


            /*axios({
                url:"http://localhost:5000/download-images",
                method:'POST',
                responseType:'blob'
            }).then(res=> {
                const fileUrl = window.URL.createObjectURL(new Blob([res.data]))
                const fileLink = document.createElement('a');
                fileLink.href = fileUrl;
                fileLink.setAttribute('download','image.jpg');
                document.body.appendChild(fileLink);
                fileLink.click();
            })*/
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