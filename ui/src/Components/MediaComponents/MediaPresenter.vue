<template>
  <div>
    <ImageCacher :tag="tag"/>
    <Galleria ref="galleria"
              :value="images"
              :fullScreen="fullScreen"
              containerStyle="max-width: 850px"
              :activeIndex.sync="activeIndex"
              :numVisible="5"
              style="max-height: 600px; max-width: 800px;"
              :showThumbnails="showThumbnails"
              :showItemNavigators="true"
              :showItemNavigatorsOnHover="true"
              :circular="true">
      <template #item="slotProps" >
        <img :src="slotProps.item.itemImageSrc"
             :alt="slotProps.item.alt"
             :style="[{'width': !fullScreen ? '100%' : '', 'display': !fullScreen ? 'block' : ''}]"/>
      </template>
      <template #caption="{item}">
        <h4 style="margin-bottom: .5rem; text-align: left; display: inline-block">{{item.title}}</h4>
        <p style="margin-bottom: .5rem; text-align: left; display: inline-block">{{item.description}}</p>
        <h1 style="margin-bottom: .5rem; text-align: left; display: inline-block">{{activeIndex + 1}}/{{images.length}}</h1>
      </template>
      <template #thumbnail="slotProps">
        <div class="p-grid p-nogutter p-justify-center p-galleria-thumbnail-container">
          <img :src="slotProps.item.thumbnailImageSrc"
               :alt="slotProps.item.alt"
               style="max-height: 60px; max-width: 80px; display: block;"/>
        </div>
      </template>
    </Galleria>
  </div>
</template>

<script lang="ts">
import {Component, Prop, Vue} from "vue-property-decorator";
import {GalleriaImageItem, GalleriaResponsiveOption, MediaContent, MediaTag} from "@/api/dto";
import ImageCacher from "@/Components/MediaComponents/ImageCacher.vue";
import CustomGalleriaFooter from "@/Components/MediaComponents/CustomGalleriaFooter.vue";
@Component({
  components: {CustomGalleriaFooter, ImageCacher}
})
export default class MediaPresenter extends Vue{
  @Prop({required: true})
  private tag: MediaTag;

  private activeIndex = 0;
  private showThumbnails = true;
  private fullScreen = false;

  private get responsiveOptions(): GalleriaResponsiveOption[]{
    return [
        new GalleriaResponsiveOption('1024px', 5),
        new GalleriaResponsiveOption('768px', 3),
        new GalleriaResponsiveOption('560px', 1)
    ];
  }

  private onThumbnailButtonClick(): void{
    console.log('onThumbnailButtonClick');
  }

  private get images(){
    if(!this.tag.linkedContent) return [];
    return this.tag.linkedContent.map((mediaContent: MediaContent): GalleriaImageItem => new GalleriaImageItem(mediaContent));
  }
}
</script>

