export class ArticleDetailedDTO {
  public id: string;
  public title: string;
  public content: string;
  public createdAt: Date;
  public authors: AppUserDTO[];
  public categories: Category[];
  public publisher: Publisher;
  public liked: boolean;
  public likeCount: number;
  public comments: Comment[];
}

export class ArticleSimpleDTO {
  public id: string;
  public title: string;
  public createdAt: Date;
  public authors: AppUserDTO[];
  public categories: Category[];
  public publisher: Publisher;
  public liked: boolean;
  public likeCount: number;
}

export class AppUserDTO {
  public id: string;
  public firstName: string;
  public lastName: string;
  public userName: string;
}

export class AppUserDetailedDTO extends AppUserDTO {
  public createdAt: Date;
  public roles: string[];
}

export class ArticleSimpleListDTO {
  public articles: ArticleSimpleDTO[];
  public hasMore: boolean;
}

export class Category {
  public id: string;
  public name: string;
}

export class Publisher {
  public id: string;
  public createdAt: Date;
  public name: string;
  public likeCount: number;
  public order: number;
}

export class Comment {
  public id: string;
  public content: string;
  public author: AppUserDTO;
}

export class IdDTO {
  public id: string;
}

export class CollectionDTO {
  public id: string;
  public title: string;
  public description: string;
  public author: AppUserDTO;
  public articles: ArticleSimpleDTO[];
  public createdAt: Date;
}
