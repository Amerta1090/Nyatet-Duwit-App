package com.nyatetduwit.presentation.category;

import com.nyatetduwit.domain.usecase.category.AddCategoryUseCase;
import com.nyatetduwit.domain.usecase.category.DeleteCategoryUseCase;
import com.nyatetduwit.domain.usecase.category.GetCategoriesUseCase;
import com.nyatetduwit.domain.usecase.category.SeedDefaultCategoriesUseCase;
import com.nyatetduwit.domain.usecase.category.UpdateCategoryUseCase;
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
public final class CategoryViewModel_Factory implements Factory<CategoryViewModel> {
  private final Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider;

  private final Provider<AddCategoryUseCase> addCategoryUseCaseProvider;

  private final Provider<UpdateCategoryUseCase> updateCategoryUseCaseProvider;

  private final Provider<DeleteCategoryUseCase> deleteCategoryUseCaseProvider;

  private final Provider<SeedDefaultCategoriesUseCase> seedDefaultCategoriesUseCaseProvider;

  public CategoryViewModel_Factory(Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<AddCategoryUseCase> addCategoryUseCaseProvider,
      Provider<UpdateCategoryUseCase> updateCategoryUseCaseProvider,
      Provider<DeleteCategoryUseCase> deleteCategoryUseCaseProvider,
      Provider<SeedDefaultCategoriesUseCase> seedDefaultCategoriesUseCaseProvider) {
    this.getCategoriesUseCaseProvider = getCategoriesUseCaseProvider;
    this.addCategoryUseCaseProvider = addCategoryUseCaseProvider;
    this.updateCategoryUseCaseProvider = updateCategoryUseCaseProvider;
    this.deleteCategoryUseCaseProvider = deleteCategoryUseCaseProvider;
    this.seedDefaultCategoriesUseCaseProvider = seedDefaultCategoriesUseCaseProvider;
  }

  @Override
  public CategoryViewModel get() {
    return newInstance(getCategoriesUseCaseProvider.get(), addCategoryUseCaseProvider.get(), updateCategoryUseCaseProvider.get(), deleteCategoryUseCaseProvider.get(), seedDefaultCategoriesUseCaseProvider.get());
  }

  public static CategoryViewModel_Factory create(
      Provider<GetCategoriesUseCase> getCategoriesUseCaseProvider,
      Provider<AddCategoryUseCase> addCategoryUseCaseProvider,
      Provider<UpdateCategoryUseCase> updateCategoryUseCaseProvider,
      Provider<DeleteCategoryUseCase> deleteCategoryUseCaseProvider,
      Provider<SeedDefaultCategoriesUseCase> seedDefaultCategoriesUseCaseProvider) {
    return new CategoryViewModel_Factory(getCategoriesUseCaseProvider, addCategoryUseCaseProvider, updateCategoryUseCaseProvider, deleteCategoryUseCaseProvider, seedDefaultCategoriesUseCaseProvider);
  }

  public static CategoryViewModel newInstance(GetCategoriesUseCase getCategoriesUseCase,
      AddCategoryUseCase addCategoryUseCase, UpdateCategoryUseCase updateCategoryUseCase,
      DeleteCategoryUseCase deleteCategoryUseCase,
      SeedDefaultCategoriesUseCase seedDefaultCategoriesUseCase) {
    return new CategoryViewModel(getCategoriesUseCase, addCategoryUseCase, updateCategoryUseCase, deleteCategoryUseCase, seedDefaultCategoriesUseCase);
  }
}
