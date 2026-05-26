package com.nyatetduwit.presentation.investment;

import com.nyatetduwit.domain.usecase.investment.AddInvestmentUseCase;
import com.nyatetduwit.domain.usecase.investment.DeactivateInvestmentUseCase;
import com.nyatetduwit.domain.usecase.investment.DeleteInvestmentUseCase;
import com.nyatetduwit.domain.usecase.investment.GetActiveInvestmentsUseCase;
import com.nyatetduwit.domain.usecase.investment.GetInvestmentByIdUseCase;
import com.nyatetduwit.domain.usecase.investment.GetTotalCostBasisUseCase;
import com.nyatetduwit.domain.usecase.investment.GetTotalInvestmentValueUseCase;
import com.nyatetduwit.domain.usecase.investment.UpdateInvestmentUseCase;
import com.nyatetduwit.domain.usecase.investment.UpdateInvestmentValueUseCase;
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
public final class InvestmentViewModel_Factory implements Factory<InvestmentViewModel> {
  private final Provider<GetActiveInvestmentsUseCase> getActiveInvestmentsUseCaseProvider;

  private final Provider<GetTotalInvestmentValueUseCase> getTotalInvestmentValueUseCaseProvider;

  private final Provider<GetTotalCostBasisUseCase> getTotalCostBasisUseCaseProvider;

  private final Provider<GetInvestmentByIdUseCase> getInvestmentByIdUseCaseProvider;

  private final Provider<AddInvestmentUseCase> addInvestmentUseCaseProvider;

  private final Provider<UpdateInvestmentUseCase> updateInvestmentUseCaseProvider;

  private final Provider<DeleteInvestmentUseCase> deleteInvestmentUseCaseProvider;

  private final Provider<DeactivateInvestmentUseCase> deactivateInvestmentUseCaseProvider;

  private final Provider<UpdateInvestmentValueUseCase> updateInvestmentValueUseCaseProvider;

  public InvestmentViewModel_Factory(
      Provider<GetActiveInvestmentsUseCase> getActiveInvestmentsUseCaseProvider,
      Provider<GetTotalInvestmentValueUseCase> getTotalInvestmentValueUseCaseProvider,
      Provider<GetTotalCostBasisUseCase> getTotalCostBasisUseCaseProvider,
      Provider<GetInvestmentByIdUseCase> getInvestmentByIdUseCaseProvider,
      Provider<AddInvestmentUseCase> addInvestmentUseCaseProvider,
      Provider<UpdateInvestmentUseCase> updateInvestmentUseCaseProvider,
      Provider<DeleteInvestmentUseCase> deleteInvestmentUseCaseProvider,
      Provider<DeactivateInvestmentUseCase> deactivateInvestmentUseCaseProvider,
      Provider<UpdateInvestmentValueUseCase> updateInvestmentValueUseCaseProvider) {
    this.getActiveInvestmentsUseCaseProvider = getActiveInvestmentsUseCaseProvider;
    this.getTotalInvestmentValueUseCaseProvider = getTotalInvestmentValueUseCaseProvider;
    this.getTotalCostBasisUseCaseProvider = getTotalCostBasisUseCaseProvider;
    this.getInvestmentByIdUseCaseProvider = getInvestmentByIdUseCaseProvider;
    this.addInvestmentUseCaseProvider = addInvestmentUseCaseProvider;
    this.updateInvestmentUseCaseProvider = updateInvestmentUseCaseProvider;
    this.deleteInvestmentUseCaseProvider = deleteInvestmentUseCaseProvider;
    this.deactivateInvestmentUseCaseProvider = deactivateInvestmentUseCaseProvider;
    this.updateInvestmentValueUseCaseProvider = updateInvestmentValueUseCaseProvider;
  }

  @Override
  public InvestmentViewModel get() {
    return newInstance(getActiveInvestmentsUseCaseProvider.get(), getTotalInvestmentValueUseCaseProvider.get(), getTotalCostBasisUseCaseProvider.get(), getInvestmentByIdUseCaseProvider.get(), addInvestmentUseCaseProvider.get(), updateInvestmentUseCaseProvider.get(), deleteInvestmentUseCaseProvider.get(), deactivateInvestmentUseCaseProvider.get(), updateInvestmentValueUseCaseProvider.get());
  }

  public static InvestmentViewModel_Factory create(
      Provider<GetActiveInvestmentsUseCase> getActiveInvestmentsUseCaseProvider,
      Provider<GetTotalInvestmentValueUseCase> getTotalInvestmentValueUseCaseProvider,
      Provider<GetTotalCostBasisUseCase> getTotalCostBasisUseCaseProvider,
      Provider<GetInvestmentByIdUseCase> getInvestmentByIdUseCaseProvider,
      Provider<AddInvestmentUseCase> addInvestmentUseCaseProvider,
      Provider<UpdateInvestmentUseCase> updateInvestmentUseCaseProvider,
      Provider<DeleteInvestmentUseCase> deleteInvestmentUseCaseProvider,
      Provider<DeactivateInvestmentUseCase> deactivateInvestmentUseCaseProvider,
      Provider<UpdateInvestmentValueUseCase> updateInvestmentValueUseCaseProvider) {
    return new InvestmentViewModel_Factory(getActiveInvestmentsUseCaseProvider, getTotalInvestmentValueUseCaseProvider, getTotalCostBasisUseCaseProvider, getInvestmentByIdUseCaseProvider, addInvestmentUseCaseProvider, updateInvestmentUseCaseProvider, deleteInvestmentUseCaseProvider, deactivateInvestmentUseCaseProvider, updateInvestmentValueUseCaseProvider);
  }

  public static InvestmentViewModel newInstance(
      GetActiveInvestmentsUseCase getActiveInvestmentsUseCase,
      GetTotalInvestmentValueUseCase getTotalInvestmentValueUseCase,
      GetTotalCostBasisUseCase getTotalCostBasisUseCase,
      GetInvestmentByIdUseCase getInvestmentByIdUseCase, AddInvestmentUseCase addInvestmentUseCase,
      UpdateInvestmentUseCase updateInvestmentUseCase,
      DeleteInvestmentUseCase deleteInvestmentUseCase,
      DeactivateInvestmentUseCase deactivateInvestmentUseCase,
      UpdateInvestmentValueUseCase updateInvestmentValueUseCase) {
    return new InvestmentViewModel(getActiveInvestmentsUseCase, getTotalInvestmentValueUseCase, getTotalCostBasisUseCase, getInvestmentByIdUseCase, addInvestmentUseCase, updateInvestmentUseCase, deleteInvestmentUseCase, deactivateInvestmentUseCase, updateInvestmentValueUseCase);
  }
}
