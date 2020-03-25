export class ArticleDetailedDTO {
  public id: string;
  public title: string;
  public content: string;
  public publishedAt: Date;
  public authors: AppUserDTO[];
}

export class ArticleSimpleDTO {
  public id: string;
  public title: string;
  public publishedAt: Date;
  public authors: AppUserDTO[];
}

export class AppUserDTO {
  public id: string;
  public firstName: string;
  public lastName: string;
  public userName: string;
}
