import {StaCo} from "./staCo";

export interface StacoResponse {
  staCoDtos: StaCo[];
  currentPage: number;
  totalRecords: number;
  totalPages: number;
}
