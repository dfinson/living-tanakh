<template>
  <div class="p-galleria-footer">
    <div class="custom-galleria-footer">
      <button type="button" class="p-button p-component p-button-icon-only">
        <span class="pi pi-list p-button-icon"></span>
        <span class="p-button-label">&nbsp;</span>
        <span class="p-ink" style="height: 33px; width: 33px; top: 1.70312px; left: -3.5px;"></span>
      </button>
      <span class="title-container">
        <span data-v-70a7af24="">{{count}}</span>
        <span data-v-70a7af24="" class="title">{{title}}</span>
        <span data-v-70a7af24="">{{description}}</span></span>
      <button @click="$emit('toggle-fullscreen')"
              type="button"
              class="fullscreen-button p-button p-component p-button-icon-only">
        <span class="pi pi-window-maximize p-button-icon"></span>
        <span class="p-button-label">&nbsp;</span>
        <span class="p-ink" style="height: 33px; width: 33px; top: -1.29688px; left: 2.48438px;"></span>
      </button>
    </div>
  </div>
</template>

<script lang="ts">
import {Component, Vue, Prop} from "vue-property-decorator";
import {GalleriaImageItem} from "@/api/dto";

@Component
export default class CustomGalleriaFooter extends Vue{

  @Prop({required: true})
  private images: GalleriaImageItem[];
  @Prop({required: true})
  private activeIndex: number;

  private get count(): string{
    return (this.activeIndex + 1) + '/' + this.images.length;
  }

  private get title(): string{
    const title = this.images[this.activeIndex].title;
    return title ? title : '';
  }

  private get description(): string{
    const description = this.images[this.activeIndex].description;
    return description ? description : '';
  }

}
</script>