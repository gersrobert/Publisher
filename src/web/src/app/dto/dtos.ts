export class ArticleDetailedDTO {
  public id: string;
  public title: string;
  public content: string;
  public publishedAt: Date;
  public authors: AppUserDTO[];
  public categories: Category[];
  public publisher: Publisher;
  public likeCount: number;
}

export class ArticleSimpleDTO {
  public id: string;
  public title: string;
  public publishedAt: Date;
  public authors: AppUserDTO[];
  public categories: Category[];
  public publisher: Publisher;
  public likeCount: number;
  public liked: boolean = false;
}

export class AppUserDTO {
  public id: string;
  public firstName: string;
  public lastName: string;
  public userName: string;
}

export class ArticleSimpleListDTO {
  public articles: ArticleSimpleDTO[];
  public numberOfArticles: number;
}

export class Category {
  public id: string;
  public name: string;
}

export class Publisher {
  public id: string;
  public name: string;
}
