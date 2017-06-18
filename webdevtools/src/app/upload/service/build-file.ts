import { Entity } from '../../shared/entity/entity';

export class BuildFile extends Entity {

    buildid: number;
    file: File;

    constructor() { 
        super();
     };
}
