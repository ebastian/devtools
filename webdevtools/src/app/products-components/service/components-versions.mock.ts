import { ComponentVersion } from './component-version';

import { ProductComponent } from './product-component';

const component1: ProductComponent = new ProductComponent();
component1.id = 1;

const component2: ProductComponent = new ProductComponent();
component2.id = 2;

export const COMPONENTS_VERSIONS: ComponentVersion[] = [{
    'id': 1,
    'major': '1',
    'minor': '0',
    'release': '0',
    'creation': new Date(),
    'death': new Date(),
    'component': component1
}, {
    'id': 2,
    'major': '1',
    'minor': '1',
    'release': '3',
    'creation': new Date(),
    'death': new Date(),
    'component': component1
}, {
    'id': 3,
    'major': '1',
    'minor': '2',
    'release': '0',
    'creation': new Date(),
    'death': null,
    'component': component1
}, {
    'id': 4,
    'major': '2',
    'minor': '0',
    'release': '2',
    'creation': new Date(),
    'death': null,
    'component': component2
}, {
    'id': 5,
    'major': '2',
    'release': '1',
    'minor': '1',
    'creation': new Date(),
    'death': null,
    'component': component2
}]