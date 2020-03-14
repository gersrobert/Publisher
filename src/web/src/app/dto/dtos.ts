export class ArticleDetailedDTO {
  public id: string;
  public title: string;
  public content: string;
  public publishedAt: Date;
  public authors: string[];
}

export class ArticleSimpleDTO {
  public id: string;
  public title: string;
  public publishedAt: Date;
  public authors: string[];
}
