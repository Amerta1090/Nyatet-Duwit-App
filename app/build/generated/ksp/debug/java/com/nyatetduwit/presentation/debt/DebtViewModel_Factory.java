package com.nyatetduwit.presentation.debt;

import com.nyatetduwit.domain.usecase.debt.AddDebtPaymentUseCase;
import com.nyatetduwit.domain.usecase.debt.AddDebtUseCase;
import com.nyatetduwit.domain.usecase.debt.DeleteDebtPaymentUseCase;
import com.nyatetduwit.domain.usecase.debt.DeleteDebtUseCase;
import com.nyatetduwit.domain.usecase.debt.GetActiveDebtsUseCase;
import com.nyatetduwit.domain.usecase.debt.GetDebtByIdUseCase;
import com.nyatetduwit.domain.usecase.debt.GetDebtPaymentsUseCase;
import com.nyatetduwit.domain.usecase.debt.UpdateDebtUseCase;
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
public final class DebtViewModel_Factory implements Factory<DebtViewModel> {
  private final Provider<GetActiveDebtsUseCase> getActiveDebtsUseCaseProvider;

  private final Provider<GetDebtByIdUseCase> getDebtByIdUseCaseProvider;

  private final Provider<AddDebtUseCase> addDebtUseCaseProvider;

  private final Provider<UpdateDebtUseCase> updateDebtUseCaseProvider;

  private final Provider<DeleteDebtUseCase> deleteDebtUseCaseProvider;

  private final Provider<GetDebtPaymentsUseCase> getDebtPaymentsUseCaseProvider;

  private final Provider<AddDebtPaymentUseCase> addDebtPaymentUseCaseProvider;

  private final Provider<DeleteDebtPaymentUseCase> deleteDebtPaymentUseCaseProvider;

  public DebtViewModel_Factory(Provider<GetActiveDebtsUseCase> getActiveDebtsUseCaseProvider,
      Provider<GetDebtByIdUseCase> getDebtByIdUseCaseProvider,
      Provider<AddDebtUseCase> addDebtUseCaseProvider,
      Provider<UpdateDebtUseCase> updateDebtUseCaseProvider,
      Provider<DeleteDebtUseCase> deleteDebtUseCaseProvider,
      Provider<GetDebtPaymentsUseCase> getDebtPaymentsUseCaseProvider,
      Provider<AddDebtPaymentUseCase> addDebtPaymentUseCaseProvider,
      Provider<DeleteDebtPaymentUseCase> deleteDebtPaymentUseCaseProvider) {
    this.getActiveDebtsUseCaseProvider = getActiveDebtsUseCaseProvider;
    this.getDebtByIdUseCaseProvider = getDebtByIdUseCaseProvider;
    this.addDebtUseCaseProvider = addDebtUseCaseProvider;
    this.updateDebtUseCaseProvider = updateDebtUseCaseProvider;
    this.deleteDebtUseCaseProvider = deleteDebtUseCaseProvider;
    this.getDebtPaymentsUseCaseProvider = getDebtPaymentsUseCaseProvider;
    this.addDebtPaymentUseCaseProvider = addDebtPaymentUseCaseProvider;
    this.deleteDebtPaymentUseCaseProvider = deleteDebtPaymentUseCaseProvider;
  }

  @Override
  public DebtViewModel get() {
    return newInstance(getActiveDebtsUseCaseProvider.get(), getDebtByIdUseCaseProvider.get(), addDebtUseCaseProvider.get(), updateDebtUseCaseProvider.get(), deleteDebtUseCaseProvider.get(), getDebtPaymentsUseCaseProvider.get(), addDebtPaymentUseCaseProvider.get(), deleteDebtPaymentUseCaseProvider.get());
  }

  public static DebtViewModel_Factory create(
      Provider<GetActiveDebtsUseCase> getActiveDebtsUseCaseProvider,
      Provider<GetDebtByIdUseCase> getDebtByIdUseCaseProvider,
      Provider<AddDebtUseCase> addDebtUseCaseProvider,
      Provider<UpdateDebtUseCase> updateDebtUseCaseProvider,
      Provider<DeleteDebtUseCase> deleteDebtUseCaseProvider,
      Provider<GetDebtPaymentsUseCase> getDebtPaymentsUseCaseProvider,
      Provider<AddDebtPaymentUseCase> addDebtPaymentUseCaseProvider,
      Provider<DeleteDebtPaymentUseCase> deleteDebtPaymentUseCaseProvider) {
    return new DebtViewModel_Factory(getActiveDebtsUseCaseProvider, getDebtByIdUseCaseProvider, addDebtUseCaseProvider, updateDebtUseCaseProvider, deleteDebtUseCaseProvider, getDebtPaymentsUseCaseProvider, addDebtPaymentUseCaseProvider, deleteDebtPaymentUseCaseProvider);
  }

  public static DebtViewModel newInstance(GetActiveDebtsUseCase getActiveDebtsUseCase,
      GetDebtByIdUseCase getDebtByIdUseCase, AddDebtUseCase addDebtUseCase,
      UpdateDebtUseCase updateDebtUseCase, DeleteDebtUseCase deleteDebtUseCase,
      GetDebtPaymentsUseCase getDebtPaymentsUseCase, AddDebtPaymentUseCase addDebtPaymentUseCase,
      DeleteDebtPaymentUseCase deleteDebtPaymentUseCase) {
    return new DebtViewModel(getActiveDebtsUseCase, getDebtByIdUseCase, addDebtUseCase, updateDebtUseCase, deleteDebtUseCase, getDebtPaymentsUseCase, addDebtPaymentUseCase, deleteDebtPaymentUseCase);
  }
}
