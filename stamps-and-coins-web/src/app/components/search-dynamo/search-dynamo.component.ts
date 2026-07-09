import {Component, computed, effect, inject, signal} from '@angular/core';
import {rxResource} from '@angular/core/rxjs-interop';
import {StaCo} from '../../model/sta-co';
import {AppService} from '../../services/app.service';
import {StaCoDynamodbService} from '../../services/sta-co-dynamodb.service';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {FormsModule} from '@angular/forms';

interface PageParams {
  page: number;
  size: number;
}

@Component({
  selector: 'app-search-dynamo',
  standalone: true,
  templateUrl: './search-dynamo.component.html',
  imports: [
    MatCardTitle,
    MatCard,
    FormsModule
  ],
  styleUrls: ['./search-dynamo.component.scss']
})
export class SearchDynamoComponent {
  private readonly staCoDynamodbService = inject(StaCoDynamodbService);
  readonly appService = inject(AppService);

  title = 'stamps-and-coins-manager';
  tableSizes = [5, 10, 15, 20];

  readonly selectedRecord = signal<StaCo | null>(null);
  readonly currentPageStaCo = signal(0);
  readonly currentPageStaCoSize = signal(5);
  readonly sortColumn = signal('description');
  readonly order = signal('asc');

  private readonly pageParams = computed<PageParams>(() => ({
    page: this.currentPageStaCo() + 1,
    size: this.currentPageStaCoSize(),
  }));

  private readonly staCosResource = rxResource({
    params: this.pageParams,
    stream: ({params}) => this.staCoDynamodbService.searchUnfilteredDynamoDb(params.page, params.size),
  });

  private readonly countResource = rxResource({
    params: this.pageParams,
    stream: () => this.staCoDynamodbService.countRecordsDynamoDb(),
  });

  readonly allStaCos = computed(() => this.staCosResource.value() ?? []);

  readonly pagesStaCos = computed(() => {
    const count = this.countResource.value();
    if (count === undefined) {
      return [];
    }
    const pages: number[] = [];
    for (let i = 0; i < count / this.currentPageStaCoSize(); i++) {
      pages.push(i + 1);
    }
    return pages;
  });

  constructor() {
    effect(() => {
      if (this.staCosResource.hasValue()) {
        this.appService.token.set('authenticated');
      }
    });
    effect(() => {
      if (this.allStaCos().length === 0 && this.currentPageStaCo() > 1) {
        this.currentPageStaCo.set(0);
      }
    });
  }

  refresh() {
    this.staCosResource.reload();
    this.countResource.reload();
  }

  onTableSizeChange(event): void {
    this.currentPageStaCo.set(0);
    this.currentPageStaCoSize.set(event.target.value);
  }

  setSelected(staCo: StaCo) {
    this.selectedRecord.set(staCo);
  }

  setEntityPage(page: number) {
    this.currentPageStaCo.set(page);
  }

  setSortColumn(sortColumn: string) {
    if (sortColumn === this.sortColumn()) {
      this.order.set(this.order() === 'asc' ? 'desc' : 'asc');
    }
    this.sortColumn.set(sortColumn);
  }

}
