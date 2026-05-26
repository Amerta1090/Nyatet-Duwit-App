package com.nyatetduwit.presentation.splitbill;

import com.nyatetduwit.domain.usecase.splitbill.AddBillUseCase;
import com.nyatetduwit.domain.usecase.splitbill.DeleteBillUseCase;
import com.nyatetduwit.domain.usecase.splitbill.GetAllBillsUseCase;
import com.nyatetduwit.domain.usecase.splitbill.GetBillByIdUseCase;
import com.nyatetduwit.domain.usecase.splitbill.MarkPersonPaidUseCase;
import com.nyatetduwit.domain.usecase.splitbill.MarkSettledUseCase;
import com.nyatetduwit.domain.usecase.splitbill.UpdateBillUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class SplitBillViewModel_Factory implements Factory<SplitBillViewModel> {
  private final Provider<GetAllBillsUseCase> getAllBillsUseCaseProvider;

  private final Provider<GetBillByIdUseCase> getBillByIdUseCaseProvider;

  private final Provider<AddBillUseCase> addBillUseCaseProvider;

  private final Provider<UpdateBillUseCase> updateBillUseCaseProvider;

  private final Provider<DeleteBillUseCase> deleteBillUseCaseProvider;

  private final Provider<MarkSettledUseCase> markSettledUseCaseProvider;

  private final Provider<MarkPersonPaidUseCase> markPersonPaidUseCaseProvider;

  public SplitBillViewModel_Factory(Provider<GetAllBillsUseCase> getAllBillsUseCaseProvider,
      Provider<GetBillByIdUseCase> getBillByIdUseCaseProvider,
      Provider<AddBillUseCase> addBillUseCaseProvider,
      Provider<UpdateBillUseCase> updateBillUseCaseProvider,
      Provider<DeleteBillUseCase> deleteBillUseCaseProvider,
      Provider<MarkSettledUseCase> markSettledUseCaseProvider,
      Provider<MarkPersonPaidUseCase> markPersonPaidUseCaseProvider) {
    this.getAllBillsUseCaseProvider = getAllBillsUseCaseProvider;
    this.getBillByIdUseCaseProvider = getBillByIdUseCaseProvider;
    this.addBillUseCaseProvider = addBillUseCaseProvider;
    this.updateBillUseCaseProvider = updateBillUseCaseProvider;
    this.deleteBillUseCaseProvider = deleteBillUseCaseProvider;
    this.markSettledUseCaseProvider = markSettledUseCaseProvider;
    this.markPersonPaidUseCaseProvider = markPersonPaidUseCaseProvider;
  }

  @Override
  public SplitBillViewModel get() {
    return newInstance(getAllBillsUseCaseProvider.get(), getBillByIdUseCaseProvider.get(), addBillUseCaseProvider.get(), updateBillUseCaseProvider.get(), deleteBillUseCaseProvider.get(), markSettledUseCaseProvider.get(), markPersonPaidUseCaseProvider.get());
  }

  public static SplitBillViewModel_Factory create(
      Provider<GetAllBillsUseCase> getAllBillsUseCaseProvider,
      Provider<GetBillByIdUseCase> getBillByIdUseCaseProvider,
      Provider<AddBillUseCase> addBillUseCaseProvider,
      Provider<UpdateBillUseCase> updateBillUseCaseProvider,
      Provider<DeleteBillUseCase> deleteBillUseCaseProvider,
      Provider<MarkSettledUseCase> markSettledUseCaseProvider,
      Provider<MarkPersonPaidUseCase> markPersonPaidUseCaseProvider) {
    return new SplitBillViewModel_Factory(getAllBillsUseCaseProvider, getBillByIdUseCaseProvider, addBillUseCaseProvider, updateBillUseCaseProvider, deleteBillUseCaseProvider, markSettledUseCaseProvider, markPersonPaidUseCaseProvider);
  }

  public static SplitBillViewModel newInstance(GetAllBillsUseCase getAllBillsUseCase,
      GetBillByIdUseCase getBillByIdUseCase, AddBillUseCase addBillUseCase,
      UpdateBillUseCase updateBillUseCase, DeleteBillUseCase deleteBillUseCase,
      MarkSettledUseCase markSettledUseCase, MarkPersonPaidUseCase markPersonPaidUseCase) {
    return new SplitBillViewModel(getAllBillsUseCase, getBillByIdUseCase, addBillUseCase, updateBillUseCase, deleteBillUseCase, markSettledUseCase, markPersonPaidUseCase);
  }
}
