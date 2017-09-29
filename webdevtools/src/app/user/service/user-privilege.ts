import { Entity } from '../../shared/entity/entity';

export class UserPrivilege extends Entity {
  
  className: string;
  description: string;
  get: boolean;
  post: boolean;
  put: boolean;
  delete: boolean;

  constructor() { 
    super();
  };
}