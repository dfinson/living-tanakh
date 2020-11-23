<template>
  <div class="content-section implementation">
    <div class="card">
      <div id="pv_id_2" class="p-galleria p-component p-galleria-thumbnails-bottom" style="max-width: 640px;">
        <div class="p-galleria-content">
          <div class="p-galleria-item-wrapper">
            <div class="p-galleria-item-container">
              <div class="p-galleria-item">
                <img :src="imageUrls[activeIndex]" :alt="imageDescriptions[activeIndex]" style="width: 100%;" />
              </div>
            </div>
          </div>
          <div class="p-galleria-thumbnail-wrapper">
            <div class="p-galleria-thumbnail-container">
              <button class="p-galleria-thumbnail-prev p-link p-disabled" disabled="" type="button">
                <span class="p-galleria-thumbnail-prev-icon pi pi-chevron-left"></span>
                <span class="p-ink"></span>
              </button>
              <div class="p-galleria-thumbnail-items-container">

                <div class="p-galleria-thumbnail-items">
                  <div class="p-galleria-thumbnail-item p-galleria-thumbnail-item-current p-galleria-thumbnail-item-active p-galleria-thumbnail-item-start">
                    <div class="p-galleria-thumbnail-item-content" tabindex="0">
                      <img src="demo/images/galleria/galleria1s.jpg" alt="Description for Image 1" />
                    </div>
                  </div>

                  <div class="p-galleria-thumbnail-item p-galleria-thumbnail-item-active">
                    <div class="p-galleria-thumbnail-item-content" tabindex="0"><img src="demo/images/galleria/galleria2s.jpg" alt="Description for Image 2" /></div>
                  </div>

                  <div class="p-galleria-thumbnail-item p-galleria-thumbnail-item-active p-galleria-thumbnail-item-end">
                    <div class="p-galleria-thumbnail-item-content" tabindex="0"><img src="demo/images/galleria/galleria5s.jpg" alt="Description for Image 5" /></div>
                  </div>

                  <div class="p-galleria-thumbnail-item">
                    <div class="p-galleria-thumbnail-item-content"><img src="demo/images/galleria/galleria6s.jpg" alt="Description for Image 6" /></div>
                  </div>
                </div>
              </div>
              <button class="p-galleria-thumbnail-next p-link" type="button">
                <span class="p-galleria-thumbnail-next-icon pi pi-chevron-right"></span>
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
import {MediaContent, MediaTag} from "@/api/dto";

@Component
export default class Gallery extends Vue{
  @Prop({required: true})
  private tag: MediaTag;

  private activeIndex = 0;

  private get imageUrls(){
    return this.tag.linkedContent?.map((content: MediaContent): string | undefined => content.signedDownloadUrl);
  }

  private get imageDescriptions(){
    return this.tag.linkedContent?.map((content: MediaContent): string => content.description ? content.description : "");
  }

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
.p-disabled,
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
.p-galleria .p-galleria-thumbnail-container {
  background: rgba(0, 0, 0, 0.9);
  padding: 1rem 0.25rem;
}
.p-galleria .p-galleria-thumbnail-container .p-galleria-thumbnail-prev,
.p-galleria .p-galleria-thumbnail-container .p-galleria-thumbnail-next {
  margin: 0.5rem;
  background-color: transparent;
  color: #f8f9fa;
  width: 2rem;
  height: 2rem;
  transition: background-color 0.2s, color 0.2s, box-shadow 0.2s;
  border-radius: 50%;
}
.p-galleria .p-galleria-thumbnail-container .p-galleria-thumbnail-prev:hover,
.p-galleria .p-galleria-thumbnail-container .p-galleria-thumbnail-next:hover {
  background: rgba(255, 255, 255, 0.1);
  color: #f8f9fa;
}
.p-galleria .p-galleria-thumbnail-container .p-galleria-thumbnail-item-content {
  transition: box-shadow 0.2s;
}
.p-galleria .p-galleria-thumbnail-container .p-galleria-thumbnail-item-content:focus {
  outline: 0 none;
  outline-offset: 0;
  box-shadow: 0 0 0 0.2rem #a6d5fa;
}
/*! CSS Used from: Embedded */
.layout-content .content-section {
  padding: 2rem;
}
.layout-content .content-section.implementation {
  background-color: var(--surface-b);
  color: var(--text-color);
}
.layout-content .card {
  background: var(--surface-e);
  padding: 2rem;
  -webkit-box-shadow: 0 2px 1px -1px rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.14), 0 1px 3px 0 rgba(0, 0, 0, 0.12);
  box-shadow: 0 2px 1px -1px rgba(0, 0, 0, 0.2), 0 1px 1px 0 rgba(0, 0, 0, 0.14), 0 1px 3px 0 rgba(0, 0, 0, 0.12);
  border-radius: 4px;
  margin-bottom: 2rem;
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
.p-galleria-thumbnail-wrapper {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: vertical;
  -webkit-box-direction: normal;
  -ms-flex-direction: column;
  flex-direction: column;
  overflow: auto;
  -ms-flex-negative: 0;
  flex-shrink: 0;
}
.p-galleria-thumbnail-next,
.p-galleria-thumbnail-prev {
  -ms-flex-item-align: center;
  align-self: center;
  -webkit-box-flex: 0;
  -ms-flex: 0 0 auto;
  flex: 0 0 auto;
  overflow: hidden;
  position: relative;
}
.p-galleria-thumbnail-next,
.p-galleria-thumbnail-next span,
.p-galleria-thumbnail-prev,
.p-galleria-thumbnail-prev span {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}
.p-galleria-thumbnail-container {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-orient: horizontal;
  -webkit-box-direction: normal;
  -ms-flex-direction: row;
  flex-direction: row;
}
.p-galleria-thumbnail-items-container {
  overflow: hidden;
}
.p-galleria-thumbnail-item,
.p-galleria-thumbnail-items {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
}
.p-galleria-thumbnail-item {
  overflow: auto;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  cursor: pointer;
  opacity: 0.5;
}
.p-galleria-thumbnail-item:hover {
  opacity: 1;
  -webkit-transition: opacity 0.3s;
  transition: opacity 0.3s;
}
.p-galleria-thumbnail-item-current {
  opacity: 1;
}
/*! CSS Used from: Embedded */
.p-component,
.p-component * {
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.p-disabled,
.p-disabled * {
  cursor: default !important;
  pointer-events: none;
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
/*! CSS Used from: Embedded */
#pv_id_2 .p-galleria-thumbnail-item {
  flex: 1 0 20%;
}
@media screen and (max-width: 1024px) {
  #pv_id_2 .p-galleria-thumbnail-item {
    flex: 1 0 20%;
  }
}
@media screen and (max-width: 768px) {
  #pv_id_2 .p-galleria-thumbnail-item {
    flex: 1 0 33.333333333333336%;
  }
}
@media screen and (max-width: 560px) {
  #pv_id_2 .p-galleria-thumbnail-item {
    flex: 1 0 100%;
  }
}
/*! CSS Used fontfaces */
@font-face {
  font-family: primeicons;
  font-display: auto;
  src: url(https://primefaces.org/primevue/showcase/fonts/primeicons.159263bf.eot);
  src: url(https://primefaces.org/primevue/showcase/fonts/primeicons.159263bf.eot#iefix) format("embedded-opentype"), url(https://primefaces.org/primevue/showcase/fonts/primeicons.2e3a6b48.ttf) format("truetype"),
  url(https://primefaces.org/primevue/showcase/fonts/primeicons.79432094.woff) format("woff"), url(https://primefaces.org/primevue/showcase/img/primeicons.4e915114.svg#primeicons) format("svg");
  font-weight: 400;
  font-style: normal;
}


</style>