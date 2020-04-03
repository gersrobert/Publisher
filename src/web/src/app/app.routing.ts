import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ArticleListComponent} from './article-list/article-list.component';
import {ArticleDetailComponent} from './article-detail/article-detail.component';
import {LoginScreenComponent} from './login-screen/login-screen.component';
import {ArticleInsertComponent} from './article-insert/article-insert.component';
import {UserDetailComponent} from './user-detail/user-detail.component';

const routes: Routes = [
  {path: '', redirectTo: 'home/articleList', pathMatch: 'full'},
  {path: 'home/articleList', component: ArticleListComponent},
  {path: 'home/insertArticle', component: ArticleInsertComponent},
  {path: 'home/articleDetail/:id', component: ArticleDetailComponent},
  {path: 'home/login', component: LoginScreenComponent},
  {path: 'home/userDetail/:id', component: UserDetailComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(
    routes
  )],
  exports: [RouterModule]
})
export class MainRoutingModule {
}
