<template>
    <section>

        <b-field grouped group-multiline>
            <v-btn  color="error" @click="deleteAll" style="margin-right: 2vw"
                    :disabled="!selectedImages.length">
               Clear All
            </v-btn>


          <!--dialog element activated when download button pressed-->
          <v-dialog
              v-model="dialog"
              scrollable
              max-width="500px"
              style="max-height: 500px"

          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                  color="primary"
                  dark
                  v-bind="attrs"

                  :disabled="!selectedImages.length" @click="activateDialog"
              >
                Download
              </v-btn>
            </template>
            <v-card style="overflow-x: hidden">
              <v-card-title>Download the following Images?</v-card-title>
              <v-card-subtitle>Total Size: {{downLoadSize}} MB</v-card-subtitle>
              <v-divider></v-divider>
              <v-card>
                <v-row justify="center" style="margin-left: 3px; margin-right: 3px">
                  <v-col
                      v-for="item in imagesToBeDownloaded"
                      :key="item.title"
                      class="d-flex child-flex"
                      cols="4"
                  >
                    <v-card style="border-radius: 5px">


                    <v-img
                        style="border-radius: 5px"
                        :src="item.thumbnailImageSrc"
                        aspect-ratio="1"
                        class="grey lighten-2"
                    >

                      <template v-slot:placeholder>
                        <v-row
                            class="fill-height ma-0"
                            align="center"
                            justify="center"
                        >
                          <v-progress-circular
                              indeterminate
                              color="grey lighten-5"
                          ></v-progress-circular>
                        </v-row>
                      </template>
                    </v-img>
                      <v-card-subtitle>{{item.title}}</v-card-subtitle>
                    </v-card>
                  </v-col>
                </v-row>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn
                      color="green darken-1"
                      text
                      @click="dialog = false"
                  >
                    Cancel
                  </v-btn>
                  <v-btn
                      color="green darken-1"
                      text
                      @click="downloadImages"
                  >
                    Confirm
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-card>
          </v-dialog>
        </b-field>



<v-row style="margin-bottom: 5px; margin-top: 15px">
                        <v-img :src="selectedImageFromTable.thumbnailImageSrc"   height="250" style="border-radius: 10px"/>
</v-row>
        <div >


          <h1 style="font-size:20px;font-family:Arial; color:#01579b;">{{selectedImages.length}} Image/s selected</h1>
          <h1 style="font-size:15px;font-family:Arial; color:#01579b;">Download Size {{downLoadSize}} MB</h1>
          <h1 style="font-size:10px;font-family:Arial; color:black;">Select from the list the images you'd like to download</h1>

          <b-table
              :data="selectedImages"
              :columns="columns"
              :selected.sync="selectedImageFromTable"
              :paginated="true"
              :per-page="3"
              checkable
              :checked-rows.sync="imagesToBeDownloaded"
              :header-checkable="false"
              focusable>
          </b-table>

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
        public page = 1;
        public pageCount = 0;
        public dialog = false;
        public imagesToBeDownloaded: GalleriaImageItem[] = [];
        public selectedImageFromTable = new GalleriaImageItem({
          id:Math.floor(Math.random() * 100) ,
          signedDownloadUrl: '',
          description:'',
          sizeInBytes:0
        });


        @Prop()
        selectedImage: GalleriaImageItem;



        public columns = [
          {
            field: 'title',
            label: 'Image Description',
            numeric: false
          },
          {
            field: 'sizeInMB',
            label: 'Size (MB)',
          }
        ]
        public headers = [
          { text: '', value: 'toBeDownloaded',align: 'start', sortable: false },
            {
          text: 'Image Description',
          value: 'title',
        },
          { text: 'Size', value: 'sizeInMB' },
       ]

      public deleteAll(): void{
        this.selectedImages = [];
        this.checkedRows = [];
        this.selectedImageFromTable = new GalleriaImageItem({
          id:Math.floor(Math.random() * 100) ,
          signedDownloadUrl: '',
          description:'',
          sizeInBytes:0
        });
        this.imagesToBeDownloaded = [];
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
                  sizeInBytes:this.selectedImage.sizeInMB! * Math.pow(1024,2)
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
                  this.imagesToBeDownloaded.push(temp);
                  this.selectedImageFromTable = temp;
                  this.$buefy.toast.open({
                      message: 'Image Added to Selected Images',
                      type: 'is-success',
                      duration: 1000
                  })
              }
              //console.log(this.selectedImages.length);
          }

        }



        public activateDialog(): void{
          this.dialog = false;
          let count = 0;
          for(let i = 0; i < this.selectedImages.length; i++){
            if(this.selectedImages[i].toBeDownloaded)
              count++
          }
          if(count > 0){
            this.dialog = true;
          }
          else{
            console.log(count)
            this.errorMessage = true;
            this.dialog = false;
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
           duration: 1000
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




        public get downLoadSize(){
          const sum = this.imagesToBeDownloaded.reduce(function (acc, obj) { return acc + obj.sizeInMB!; }, 0);
          console.log(sum)
          return sum.toFixed(2);
        }



      // http://localhost:5000/download-images?keys=['image1.jpg', 'image2.png', ...]
        public downloadImages(): void{
          this.downloading = true
          this.dialog = false;
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