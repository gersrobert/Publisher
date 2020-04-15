import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MainRoutingModule} from './app.routing';

import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import {HeaderComponent} from './components/header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatListModule} from '@angular/material/list';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatFormFieldModule} from '@angular/material/form-field';
import { ArticleListComponent } from './article-list/article-list.component';
import { ArticleDetailComponent } from './article-detail/article-detail.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { LoginScreenComponent } from './login-screen/login-screen.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import { ArticleInsertComponent } from './article-insert/article-insert.component';
import {MatCardModule} from '@angular/material/card';
import {MatChipsModule} from '@angular/material/chips';
import { UserDetailComponent } from './user-detail/user-detail.component';
import {ShowdownModule} from 'ngx-showdown';
import {MatRippleModule} from '@angular/material/core';
import {LoginRedirect} from './core/login-redirect';
import { PublisherDetailComponent } from './publisher-detail/publisher-detail.component';
import { RegisterUserComponent } from './register-user/register-user.component';
import { TopPublishersComponent } from './top-publishers/top-publishers.component';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ArticleUpdateComponent } from './article-update/article-update.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    ArticleListComponent,
    ArticleDetailComponent,
    LoginScreenComponent,
    ArticleInsertComponent,
    UserDetailComponent,
    PublisherDetailComponent,
    RegisterUserComponent,
    TopPublishersComponent,
    ArticleUpdateComponent,
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        MatIconModule,
        MatMenuModule,
        MatButtonModule,
        MatListModule,
        MatExpansionModule,
        MatFormFieldModule,
        MainRoutingModule,
        MatPaginatorModule,
        FormsModule,
        ReactiveFormsModule,
        MatInputModule,
        MatCardModule,
        MatChipsModule,
        ShowdownModule,
        MatRippleModule,
        MatProgressSpinnerModule
    ],
  providers: [LoginRedirect],
  bootstrap: [AppComponent]
})
export class AppModule { }
