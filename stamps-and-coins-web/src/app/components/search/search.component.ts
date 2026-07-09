import {Component, inject, OnInit, signal} from '@angular/core';
import {StaCo} from '../../model/sta-co';
import {StaCoService} from '../../services/sta-co.service';
import {AppService} from '../../services/app.service';
import {MatCard, MatCardTitle} from '@angular/material/card';
import {FormsModule} from '@angular/forms';
import {MatInput} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

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
export class SearchComponent implements OnInit {
  private readonly staCoService = inject(StaCoService);
  readonly appService = inject(AppService);

  title = 'stamps-and-coins-manager';
  term = '';
  tableSizes = [5, 10, 15, 20];

  readonly allStaCos = signal<StaCo[]>([]);
  readonly selectedRecord = signal<StaCo | null>(null);
  readonly validationSize = signal(true);
  readonly validationContent = signal(true);
  readonly currentPageStaCo = signal(0);
  readonly currentPageStaCoSize = signal(5);
  readonly pagesStaCos = signal<number[]>([]);
  readonly sortColumn = signal('description');
  readonly order = signal('asc');

  ngOnInit(): void {
    this.fetchData();
  }

  refresh() {
    this.validationSize.set(!(this.term && this.term.length > 10) || !this.term);
    this.validationContent.set(this.term && (/^[a-zA-Z0-9 ]*$/.test(this.term) || this.term.length === 0) || !this.term);
    if (this.validationSize() && this.validationContent()) {
      this.fetchData();
    }
  }

  private fetchData() {
    if (this.term) {
      this.staCoService.searchByTerm(
        this.term,
        this.currentPageStaCo(),
        this.currentPageStaCoSize(),
        this.sortColumn(),
        this.order())
        .subscribe(data => this.processResponse(data))
    } else {
      this.staCoService.searchUnfiltered(
        this.currentPageStaCo(),
        this.currentPageStaCoSize(),
        this.sortColumn(),
        this.order())
        .subscribe(data => this.processResponse(data))
    }
  }

  private processResponse(data: StaCo[]) {
    this.appService.token.set('authenticated');
    this.allStaCos.set(data);
    if (this.allStaCos().length == 0 && this.currentPageStaCo() > 1) {
      this.currentPageStaCo.set(0);
      this.fetchData()
    }

    if (this.term) {
      this.staCoService.countByTerm(
        this.term,
        this.currentPageStaCo(),
        this.currentPageStaCoSize(),
        this.sortColumn(),
        this.order())
        .subscribe(data => this.makePages(data))
    } else {
      this.staCoService.countUnfiltered(
        this.currentPageStaCo(),
        this.currentPageStaCoSize(),
        this.sortColumn(),
        this.order())
        .subscribe(data => this.makePages(data))
    }
  }

  onTableSizeChange(event): void {
    this.currentPageStaCo.set(0);
    this.currentPageStaCoSize.set(event.target.value);
    this.fetchData();
  }

  setSelected(staCo: StaCo) {
    this.selectedRecord.set(staCo);
  }

  setEntityPage(page: number) {
    this.currentPageStaCo.set(page);
    this.fetchData();
  }

  setSortColumn(sortColumn: string) {
    if (sortColumn === this.sortColumn()) {
      this.order.set(this.order() === 'asc' ? 'desc' : 'asc');
    }
    this.sortColumn.set(sortColumn);
    this.fetchData();
  }

  private makePages(data: number) {
    const pages: number[] = [];
    for (let i = 0; i < data / this.currentPageStaCoSize(); i++) {
      pages.push(i + 1);
    }
    this.pagesStaCos.set(pages);
  }
}
