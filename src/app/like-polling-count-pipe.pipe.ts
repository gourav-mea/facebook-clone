import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'likePollingCountPipe'
})
export class LikePollingCountPipePipe implements PipeTransform {

  transform(likeCountData: { 'postId': number, 'likeCount' : string }[] | null, postId:number): string {
    const likeCount = likeCountData?.find((item)=> item.postId === postId);
    console.log(likeCountData);
    console.log("pipe", likeCount?.likeCount);
    return likeCount? likeCount.likeCount : '';
  }

}
