<template>
  <div>
    <!-- preload all images -->
    <img v-for="(content, index) in tag.linkedContent" :key="index" :src="content.signedDownloadUrl" style="display: none;" alt=""/>
    <div class="p-galleria-mask p-component-overlay" style="z-index: 1018;">
      <div class="p-galleria p-component p-galleria-fullscreen" style="max-width: 850px; z-index: 1017;">
        <!-- close gallery button -->
        <button @click="$emit('closed-media-tag-modal')" type="button" class="p-galleria-close p-link">
          <span class="p-galleria-close-icon pi pi-times"></span>
          <span class="p-ink"></span>
        </button>

        <div class="p-galleria-content">
          <h1 style="color: white; margin-bottom: 20%;">Images for {{tag.key}}</h1>
          <div class="p-galleria-item-wrapper">
            <div class="p-galleria-item-container">
              <!-- previous image button -->
              <button @click="previousImage()" type="button" class="p-galleria-item-prev p-galleria-item-nav p-link">
                <span class="p-galleria-item-prev-icon pi pi-chevron-left"></span>
                <span class="p-ink"></span>
              </button>
              <!-- current image display item -->
              <div>
                <b-loading v-model="isLoading" v-show="isLoading" :can-cancel="true"></b-loading>
              <div class="p-galleria-item" v-show="!isLoading"> <img :src="tag.linkedContent[activeIndex].signedDownloadUrl"
                                                :alt="tag.linkedContent[activeIndex].key"
                                                style="width: 100%; display: block;" v-on:load="isLoading = false"/>
              </div>
              </div>
              <!-- next image button -->
              <button @click="nextImage()" type="button" class="p-galleria-item-next p-galleria-item-nav p-link">
                <span class="p-galleria-item-next-icon pi pi-chevron-right"></span>
                <span class="p-ink"></span>
              </button>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<script lang="ts">

import {Vue, Component, Prop} from "vue-property-decorator";
import {MediaTag} from "@/api/dto";


@Component
export default class Gallery extends Vue{

  public isLoading = true;
  @Prop({required: true})
  private tag: MediaTag;
  private activeIndex = 0;

  private previousImage(): void{
    //alert('previousImage')
    if(this.activeIndex > 0)
      this.activeIndex--;
    else
      this.activeIndex = this.numImages - 1;
  }

  private nextImage(): void{
    //alert('nextImage')
    if(this.activeIndex < this.numImages) this.activeIndex++;
    else this.activeIndex = 0;
  }


  private get numImages(): number{
    return this.tag.linkedContent ? this.tag.linkedContent.length : 0;
  }

  /*private activeThumbnailsStartIndex = 0;
  private activeThumbnailsEndIndex = 3;



  private thumbnailClasses(index: number): string{
    if(index === this.activeThumbnailsStartIndex)
      return 'p-galleria-thumbnail-item p-galleria-thumbnail-item-current p-galleria-thumbnail-item-active p-galleria-thumbnail-item-start';
    else if(index > this.activeThumbnailsStartIndex && index < this.activeThumbnailsEndIndex)
      return 'p-galleria-thumbnail-item p-galleria-thumbnail-item-active';
    else if(index === this.activeThumbnailsEndIndex)
      return 'p-galleria-thumbnail-item p-galleria-thumbnail-item-active p-galleria-thumbnail-item-end';
    else
      return 'p-galleria-thumbnail-item-content'
  }

  private get items(): GalleriaItemTemplate[]{
    if(!this.tag || !this.tag.linkedContent) {
      return [];
    }
      return this.tag.linkedContent.map((content: MediaContent): GalleriaItemTemplate => new GalleriaItemTemplate(content));
  }*/
}
</script>

<style scoped>

/*! CSS Used from: https://primefaces.org/primevue/showcase/themes/saga-blue/theme.css */
* {
  box-sizing: border-box;
}
.p-component {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
  font-size: 1rem;
  font-weight: normal;
}
.p-component-overlay {
  background-color: rgba(0, 0, 0, 0.4);
  transition-duration: 0.2s;
}
.p-component:disabled {
  opacity: 0.6;
}
.pi {
  font-size: 1rem;
}
.p-link {
  font-size: 1rem;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Helvetica, Arial, sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol";
  border-radius: 3px;
}
.p-link:focus {
  outline: 0 none;
  outline-offset: 0;
  box-shadow: 0 0 0 0.2rem #a6d5fa;
}
::-webkit-input-placeholder {
  color: #6c757d;
}
:-moz-placeholder {
  color: #6c757d;
}
::-moz-placeholder {
  color: #6c757d;
}
:-ms-input-placeholder {
  color: #6c757d;
}
.p-galleria .p-galleria-close {
  margin: 0.5rem;
  background: transparent;
  color: #f8f9fa;
  width: 4rem;
  height: 4rem;
  transition: background-color 0.2s, color 0.2s, box-shadow 0.2s;
  border-radius: 50%;
}
.p-galleria .p-galleria-close .p-galleria-close-icon {
  font-size: 2rem;
}
.p-galleria .p-galleria-close:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #f8f9fa;
}
.p-galleria .p-galleria-item-nav {
  background: transparent;
  color: #f8f9fa;
  width: 4rem;
  height: 4rem;
  transition: background-color 0.2s, color 0.2s, box-shadow 0.2s;
  border-radius: 3px;
  margin: 0 0.5rem;
}
.p-galleria .p-galleria-item-nav .p-galleria-item-prev-icon,
.p-galleria .p-galleria-item-nav .p-galleria-item-next-icon {
  font-size: 2rem;
}
.p-galleria .p-galleria-item-nav:not(.p-disabled):hover {
  background: rgba(255, 255, 255, 0.1);
  color: #f8f9fa;
}
.p-galleria-mask.p-component-overlay {
  background-color: rgba(0, 0, 0, 0.9);
}
/*! CSS Used from: Embedded */
.p-galleria-content,
.p-galleria-item-wrapper {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
}
.p-galleria-item-wrapper {
  position: relative;
}
.p-galleria-item-container {
  position: relative;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  height: 100%;
}
.p-galleria-item-nav {
  position: absolute;
  top: 50%;
  margin-top: -0.5rem;
  display: -webkit-inline-box;
  display: -ms-inline-flexbox;
  display: inline-flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  overflow: hidden;
}
.p-galleria-item-prev {
  left: 0;
  border-top-left-radius: 0;
  border-bottom-left-radius: 0;
}
.p-galleria-item-next {
  right: 0;
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
}
.p-galleria-item {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  height: 100%;
  width: 100%;
}
.p-galleria-mask {
  position: fixed;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: transparent;
  -webkit-transition-property: background-color;
  transition-property: background-color;
}
.p-galleria-close,
.p-galleria-mask {
  top: 0;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
}
.p-galleria-close {
  position: absolute;
  right: 0;
  overflow: hidden;
}
.p-galleria-mask .p-galleria-item-nav {
  position: fixed;
  top: 50%;
  margin-top: -0.5rem;
}
/*! CSS Used from: Embedded */
.p-component,
.p-component * {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.p-component-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
button {
  border-radius: 0;
}
.p-link {
  text-align: left;
  background-color: transparent;
  margin: 0;
  padding: 0;
  border: none;
  cursor: pointer;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
}
/*! CSS Used from: Embedded */
.p-ink {
  display: block;
  position: absolute;
  background: hsla(0, 0%, 100%, 0.5);
  border-radius: 100%;
  -webkit-transform: scale(0);
  transform: scale(0);
}
/*! CSS Used from: Embedded */
.pi {
  font-family: primeicons;
  speak: none;
  font-style: normal;
  font-weight: 400;
  font-variant: normal;
  text-transform: none;
  line-height: 1;
  display: inline-block;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
.pi:before {
  --webkit-backface-visibility: hidden;
  -webkit-backface-visibility: hidden;
  backface-visibility: hidden;
}
.pi-chevron-left:before {
  content: "\e900";
}
.pi-chevron-right:before {
  content: "\e901";
}
.pi-times:before {
  content: "\e90b";
}

@font-face {
  font-family: primeicons;
  font-display: auto;
  src: url(https://maayan-assets.s3.eu-central-1.amazonaws.com/assets/primeicons.159263bf.eot);
  src: url(https://maayan-assets.s3.eu-central-1.amazonaws.com/assets/primeicons.159263bf.eot#iefix) format("embedded-opentype"), url(https://maayan-assets.s3.eu-central-1.amazonaws.com/assets/primeicons.2e3a6b48.ttf) format("truetype"), url(https://maayan-assets.s3.eu-central-1.amazonaws.com/assets/primeicons.79432094.woff) format("woff"), url(https://maayan-assets.s3.eu-central-1.amazonaws.com/assets/primeicons.4e915114.svg#primeicons) format("svg");
  font-weight: 400;
  font-style: normal;
}
</style>