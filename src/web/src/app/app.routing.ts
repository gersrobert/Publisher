import {NgModule} from '@angular/core';
import {Router, RouterModule, Routes} from '@angular/router';
import {ArticleListComponent} from './article-list/article-list.component';
import {ArticleDetailComponent} from './article-detail/article-detail.component';
import {LoginScreenComponent} from './login-screen/login-screen.component';
import {ArticleInsertComponent} from './article-insert/article-insert.component';
import {UserDetailComponent} from './user-detail/user-detail.component';
import {LoginRedirect} from './core/login-redirect';
import {PublisherDetailComponent} from './publisher-detail/publisher-detail.component';

const routes: Routes = [
  {path: '', redirectTo: 'home/articleList', pathMatch: 'full'},
  {path: 'home/articleList', component: ArticleListComponent, canActivate: [LoginRedirect]},
  {path: 'home/insertArticle', component: ArticleInsertComponent, canActivate: [LoginRedirect]},
  {path: 'home/articleDetail/:id', component: ArticleDetailComponent, canActivate: [LoginRedirect]},
  {path: 'home/login', component: LoginScreenComponent},
  {path: 'home/userDetail/:id', component: UserDetailComponent, canActivate: [LoginRedirect]},
  {path: 'home/publisherDetail/:id', component: PublisherDetailComponent, canActivate: [LoginRedirect]},
];

@NgModule({
  imports: [RouterModule.forRoot(
    routes
  )],
  exports: [RouterModule]
})
export class MainRoutingModule {
}
