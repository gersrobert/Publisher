import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ArticleListComponent} from './article-list/article-list.component';
import {ArticleDetailComponent} from './article-detail/article-detail.component';

const routes: Routes = [
  {path: '', redirectTo: 'home/articleList', pathMatch: 'full'},
  {path: 'home/articleList', component: ArticleListComponent},
  {path: 'home/articleDetail/:id', component: ArticleDetailComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(
    routes
  )],
  exports: [RouterModule]
})
export class MainRoutingModule {
}
