export class ArticleDetailedDTO {
  public title: string;
  public content: string;
  public publishedAt: Date;
  public authors: string[];
}

export class ArticleSimpleDTO {
  public title: string;
  public publishedAt: Date;
  public authors: string[];
}
