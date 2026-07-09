import {Component, computed, effect, inject, signal} from '@angular/core';
import {rxResource} from '@angular/core/rxjs-interop';
import {StaCo} from '../../model/sta-co';
import {StaCoService} from '../../services/sta-co.service';
import {AppService} from '../../services/app.service';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {FormsModule} from '@angular/forms';
import {MatInput} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

interface SearchParams {
  term: string;
  page: number;
  size: number;
  sortColumn: string;
  order: string;
}

@Component({
  selector: 'app-search',
  standalone: true,
  templateUrl: './search.component.html',
  imports: [
    MatCard,
    MatCardTitle,
    FormsModule,
    MatInput,
    MatFormFieldModule
  ],
  styleUrls: ['./search.component.scss']
})
export class SearchComponent {
  private readonly staCoService = inject(StaCoService);
  readonly appService = inject(AppService);

  title = 'stamps-and-coins-manager';
  term = '';
  tableSizes = [5, 10, 15, 20];

  readonly selectedRecord = signal<StaCo | null>(null);
  readonly validationSize = signal(true);
  readonly validationContent = signal(true);
  readonly currentPageStaCo = signal(0);
  readonly currentPageStaCoSize = signal(5);
  readonly sortColumn = signal('description');
  readonly order = signal('asc');
  private readonly searchTerm = signal('');

  private readonly searchParams = computed<SearchParams>(() => ({
    term: this.searchTerm(),
    page: this.currentPageStaCo(),
    size: this.currentPageStaCoSize(),
    sortColumn: this.sortColumn(),
    order: this.order(),
  }));

  private readonly staCosResource = rxResource({
    params: this.searchParams,
    stream: ({params}) => params.term
      ? this.staCoService.searchByTerm(params.term, params.page, params.size, params.sortColumn, params.order)
      : this.staCoService.searchUnfiltered(params.page, params.size, params.sortColumn, params.order),
  });

  private readonly countResource = rxResource({
    params: this.searchParams,
    stream: ({params}) => params.term
      ? this.staCoService.countByTerm(params.term, params.page, params.size, params.sortColumn, params.order)
      : this.staCoService.countUnfiltered(params.page, params.size, params.sortColumn, params.order),
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
    this.validationSize.set(!(this.term && this.term.length > 10) || !this.term);
    this.validationContent.set(this.term && (/^[a-zA-Z0-9 ]*$/.test(this.term) || this.term.length === 0) || !this.term);
    if (this.validationSize() && this.validationContent()) {
      this.searchTerm.set(this.term);
    }
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
