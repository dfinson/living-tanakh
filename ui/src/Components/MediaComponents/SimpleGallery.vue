<template>
  <v-app>

    <v-main style="background-color: black">

      <v-row>

        <v-flex>


          <!-- the fullscreen image viewing component modal-->

            <div class="text-center" >
              <b-modal
                  v-model="isImageModalActive"

                  full-screen

                  :can-cancel="false">



                <v-carousel hide-delimiters  v-model="imageIndex" height="100%">

                  <v-carousel-item

                      v-for="(item,i) in images"
                      :key="i"
                      :src="getImgUrl(i)"
                      style="border-radius: 10px; background-color: black;"
                      transition="slide-y-transition"
                      contain
                  >

                      <b-field>
                        <b-switch v-model="highResActive" type="is-success" style="position: absolute;top:25px;
           left: 105px"> <span style="color: white">HD</span></b-switch>
                      </b-field>

                    <v-btn style="position: absolute;top:
                20px;right: 100px"
                        class="ma-2"
                        @click="isImageModalActive = false"
                        dark
                    >
                      <v-icon
                          dark
                          center
                      >
                        mdi-close
                      </v-icon>
                    </v-btn>

                    <input type="checkbox" size="is-small"
                           style=" width: 25px; border:1px;
                padding: 1px; height: 25px;position: absolute;top:
                20px;right: 50px; border-radius: 4px; background-color: transparent; margin-top: 15px "
                           :id="i" :value="item.title"  v-model="imagesAddedToPreview"  @change="sendImageOfOneTagToPreviewSelectorFromFullScreen(item)"               />

                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <div class="tooltip" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;top: 20px; left: 190px;">


                          <v-icon style="background-color: darkcyan; border-radius: 20px"
                                  dark
                                  center
                                  large
                                  v-bind="attrs"
                                  v-on="on"
                          >
                            mdi-information
                          </v-icon>
                        </div>
                      </template>
                      <span>  This will contain a description for {{images[i].title}}</span>
                    </v-tooltip>


                  </v-carousel-item>

                </v-carousel>

              </b-modal>
            </div>

          <v-row justify="end" style="margin-top: 15px; margin-bottom: 5px; margin-right: 35px" v-if="images.length !== 0">
            <b-field>
              <b-switch v-model="highResActive" type="is-success"> <span style="color: white">HD</span></b-switch>
            </b-field>
          </v-row>

          <!-- main image viewing component-->

          <v-carousel hide-delimiters height="580" v-model="imageIndex" v-if="images.length !== 0" >

            <v-carousel-item

                v-for="(item,i) in images"
                :key="i"
                :src="getImgUrl(i)"
                style="border-radius: 10px"
                @click="isImageModalActive = true"
                contain
                transition="slide-y-transition"

            >

              <input type="checkbox" size="is-small"
                     style=" width: 25px; border:1px;
                padding: 1px; height: 25px;position: absolute;top:
                20px;right: 50px; border-radius: 4px; background-color: transparent; margin-top: 15px "
                     :id="i" :value="item.title"  v-model="imagesAddedToPreview" @change="isImageModalActive = false, sendImageOfOneTagToPreviewSelector(item)"  />
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
              <div class="tooltip" style=" width: 35px; border:1px; padding: 1px; height: 35px;position: absolute;top: 20px; left: 120px;">


                  <v-icon style="background-color: darkcyan; border-radius: 20px"
                      dark
                      center
                      large
                      v-bind="attrs"
                      v-on="on"
                  >
                    mdi-information
                  </v-icon>
                </div>
                </template>
                <span>  This will contain a description for {{images[i].title}}</span>
              </v-tooltip>




            </v-carousel-item>
          </v-carousel>

          <!-- if there are no images associated with selected content - display logo-->
          <v-row v-else justify="center" style="background-color: lightgrey; margin-top: 12px; margin-bottom: 10px">
            <img
                src="https://maayan-assets.s3.eu-central-1.amazonaws.com/Ma'ayan+Logo05B.png"
                style="border-radius: 10px"
            />
          </v-row>


        </v-flex>

      </v-row>

      <!--option to close/open bottom carousel-->
      <v-row justify="center" ><a v-if="images.length !== 0" @click="carouselButtonClicked" style="font-size: 15px" >Gallery</a></v-row>
      <!--bottom image carousel component-->
<v-row  v-if="showCarousel " justify="center" style="margin-top: -5px">



    <v-slide-group v-if="images.length !== 0"
        dark
        class="pa-4"
        center-active
        show-arrows
    >
      <v-slide-item
          v-for="(item,i) in images"   :key="i"
          v-slot="{ active, toggle }"
      >



          <v-img :src="getThumbnailUrl(i)" height="112" width="140" @click="sendToCarousel(i)" v-on:click="toggle"  >
            <input type="checkbox"
                   size="is-small"
                   style=" width: 15px;
              border:1px; padding: 1px; height: 15px;position: absolute;top: 5px;right: 5px;
               border-radius: 4px; background-color: transparent; "
                   :id="i"  :value="item.title"  v-model="imagesAddedToPreview" @change="sendImageOfOneTagToPreviewSelector(item)"

            />

            <template v-slot:placeholder>
              <v-row
                  class="fill-height ma-0"
                  align="center"
                  justify="center"
              >
                <v-progress-circular
                    indeterminate
                    color="light-blue lighten-1"
                ></v-progress-circular>
              </v-row>
            </template>

          </v-img>


      </v-slide-item>
    </v-slide-group>


</v-row>

    </v-main >
  </v-app>
</template>


<script lang='ts'>
import {Component, Vue, Prop, Watch} from 'vue-property-decorator'
import {GalleriaImageItem, MediaContent, MediaTag} from "@/api/dto";

@Component
export default class SimpleGallery extends Vue {


  public highResActive = false;

  @Prop({required: true})
  private tags: MediaTag[];

  public get images() {
    if (this.tags.some(tag => !tag.linkedContent)) return [];
    return (this.tags.flatMap(x => x.linkedContent).map(x => new GalleriaImageItem(x!)));
  }


  public checkMark(image: GalleriaImageItem): boolean{
    return this.imagesAddedToPreview.includes(image.title);
  }

  public getImgUrl(index: number): string {
    if (this.images[index] !== undefined && this.images.length !== 0) {
      if(!this.highResActive)
          return this.images[index].itemImageSrc;
      else
        return this.images[index].highResURL!;
    } else return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
  }

  public getThumbnailUrl(index: number): string {
    if (this.images[index] !== undefined && this.images.length !== 0) {

        return this.images[index].itemImageSrc;

    } else return "https://maayan-assets.s3.eu-central-1.amazonaws.com/MaayanLogo.jpeg";
  }

  /*
   id: number;
      mediaTag?: MediaTag;
      mediaType?: MediaType;
      description?: string;
      key?: string;
      signedDownloadUrl?: string;
      signedUploadUrl?: string;
      mimeType?: string;
      signedThumbnailUrl?: string;
   */
  public overlay = false;
  public imageIndex = 0;
  public imagesAddedToPreview: string[] = [];
  public isImageModalActive = false;
  public showCarousel = true;
  public displayTooltip = false;

  public sendToCarousel(index: number): void {
    this.imageIndex = index;
  }




  public carouselButtonClicked(): void{
    this.showCarousel = !this.showCarousel;
  }

  public sendImageOfOneTagToPreviewSelector(image: GalleriaImageItem): void {
    this.isImageModalActive = false;
    //if images have been sent before we won't create a paradox

      //if this image has never been sent before to previewSelector - we can send to be added to preview selector
      if (this.imagesAddedToPreview.includes(image.title)) {
        //console.log(this.images[index].itemImageSrc)
        this.$emit('send-image-to-preview-selector', image)
        //this.imagesAddedToPreview.push(this.images[index].title)
      }
      else {
       // console.log(this.images[index].itemImageSrc)
        //if it's already been sent we need to delete it from here and from the previewSelector - we can just send the title as that's a unique id
        if (!this.imagesAddedToPreview.includes(image.title)) {
          //generate event to preview selector via dashboard to remove the image in question
          console.log("Simple Gallery delete image" + image.title )
          this.$emit('remove-image-from-preview-selector', image.title)
          //use filter function to remove from array here:
       /*   const title = this.images[index].title;
          this.imagesAddedToPreview = this.imagesAddedToPreview.filter(function (link) {
            return link !== title;
          });*/
        }

    }


  }
  public sendImageOfOneTagToPreviewSelectorFromFullScreen(image: GalleriaImageItem): void {
    //if images have been sent before we won't create a paradox

    //if images have been sent before we won't create a paradox

    //if this image has never been sent before to previewSelector - we can send to be added to preview selector
    if (this.imagesAddedToPreview.includes(image.title)) {
      //console.log(this.images[index].itemImageSrc)
      this.$emit('send-image-to-preview-selector', image)
      //this.imagesAddedToPreview.push(this.images[index].title)
    }
    else {
      // console.log(this.images[index].itemImageSrc)
      //if it's already been sent we need to delete it from here and from the previewSelector - we can just send the title as that's a unique id
      if (!this.imagesAddedToPreview.includes(image.title)) {
        //generate event to preview selector via dashboard to remove the image in question
        console.log("Simple Gallery delete image" + image.title )
        this.$emit('remove-image-from-preview-selector', image.title)
        //use filter function to remove from array here:
        /*   const title = this.images[index].title;
           this.imagesAddedToPreview = this.imagesAddedToPreview.filter(function (link) {
             return link !== title;
           });*/
      }



      this.isImageModalActive = true;

    }
  }




  public hideCarousel(): void {
    this.showCarousel = !this.showCarousel;
  }

  public activateTooltip(): void {
    this.isImageModalActive = false;
    this.displayTooltip = true;
  }

  public deactivateTooltip(): void{
    this.displayTooltip = false;
    this.isImageModalActive = false;
  }

  public uncheckAll(): void{
    this.imagesAddedToPreview = [];
  }








//this.$set(this.someObject, 'b', 2)
}
</script>


<style scoped>
.level{
  margin-top: 10px;
}
a:hover {
  text-decoration: none;
}

::-webkit-scrollbar{
  width: 0;
}

/* Track */
::-webkit-scrollbar-track {
  box-shadow: transparent;
  border-radius: 10px;
  width: 0;
  height: 0;
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: transparent;
  border-radius: 10px;
  width: 0;
}


</style>