import pandas as pd
import numpy as np
from sklearn.preprocessing import OneHotEncoder, MinMaxScaler
from sklearn.compose import ColumnTransformer
from sklearn.pipeline import Pipeline


def build_feature_matrix(ads: pd.DataFrame):
    """
    Build a numerical feature matrix for ads using categorical one-hot encoding
    and scaling of numeric features.

    Args:
        ads (pd.DataFrame): DataFrame with ad features.

    Returns:
        tuple:
            - X (np.ndarray): feature matrix
            - id_to_idx (dict): mapping from ad_id to row index
            - idx_to_id (dict): reverse mapping from row index to ad_id
    """
    # Define feature groups
    categorical = ["brand", "model", "fuel_type", "transmission", "body_type", "emission_type"]
    numeric = ["engine_power", "year", "kilometers", "price"]

    # Ensure required columns exist
    for col in categorical + numeric + ["id"]:
        if col not in ads.columns:
            raise ValueError(f"Missing expected column '{col}' in ads dataframe")

    # Column transformer
    transformer = ColumnTransformer([
        ("cat", OneHotEncoder(handle_unknown="ignore"), categorical),
        ("num", MinMaxScaler(), numeric)
    ])

    # Build pipeline
    pipeline = Pipeline([("transformer", transformer)])

    X = pipeline.fit_transform(ads[categorical + numeric])

    # Build lookup dicts
    id_to_idx = {ad_id: idx for idx, ad_id in enumerate(ads["id"])}
    idx_to_id = {idx: ad_id for ad_id, idx in id_to_idx.items()}

    return X, id_to_idx, idx_to_id
